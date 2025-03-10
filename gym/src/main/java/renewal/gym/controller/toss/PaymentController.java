package renewal.gym.controller.toss;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import renewal.gym.config.TossPaymentsConfig;
import renewal.gym.controller.argument.Login;
import renewal.gym.domain.*;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.SelectedGymForm;
import renewal.gym.dto.pay.*;
import renewal.gym.dto.register.ChildRegisterForm;
import renewal.gym.repository.MemberRepository;
import renewal.gym.service.LoginService;
import renewal.gym.service.PaymentService;
import renewal.gym.service.child.ChildRegisterService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static renewal.gym.domain.PayStatus.ABORTED;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final MemberRepository memberRepository;
    private final PaymentService paymentService;
    private final ChildRegisterService childRegisterService;
    private final TossPaymentsConfig tossPaymentsConfig;
    private final LoginService loginService;

    @GetMapping("/success")
    public String success(@RequestParam String orderId, @Login LoginUserSession session, HttpSession saveData, Model model) {
        ChildRegisterForm childRegisterForm = (ChildRegisterForm)saveData.getAttribute("save" + orderId);

        model.addAttribute("selectedGym", new SelectedGymForm(childRegisterForm.getGymId(), childRegisterForm.getGymName()));

        return "pay/success";
    }

    private Child createChild(ChildRegisterForm childRegisterForm) {

        return new Child(childRegisterForm.getName(), childRegisterForm.getPhone(),
                Integer.parseInt(childRegisterForm.getAge()), childRegisterForm.getGender());
    }

    @GetMapping("/fail")
    public String fail(@RequestParam String message, @RequestParam Integer code, Model model) {

        model.addAttribute("message", message);
        model.addAttribute("code", code);

        return "pay/fail";
    }

    private final ObjectMapper objectMapper;

    @ResponseBody
    @PostMapping("/pay/saveAmount")
    public void saveAmount (@RequestBody SavePayAmount savePayAmount, HttpSession session){
        session.setAttribute(savePayAmount.getOrderId(), savePayAmount.getPayAmount());

        log.info("Save pay amount successful");
    }

    @PostMapping("/pay/verifyAmount")
    public ResponseEntity<?> verifyAmount(@RequestBody SavePayAmount savePayAmount, HttpSession session){
        String savedAmount = (String)session.getAttribute(savePayAmount.getOrderId());

        if(!savedAmount.equals(savePayAmount.getPayAmount())){
            return ResponseEntity.badRequest().body(PaymentErrorResponse.builder().code(400).message("결제 금액 정보가 유효하지 않습니다.").build());
        }

        log.info("Check pay amount successful");
        return ResponseEntity.ok("Payment is valid");
    }

    @PostMapping("/confirm")
    public ResponseEntity confirm(@RequestBody PayRequest payRequest, @Login LoginUserSession userSession, HttpSession session) throws IOException, InterruptedException {
        log.debug("Confirm pay request");
        HttpResponse response = requestConfirm(payRequest);
        ChildRegisterForm childInfo = (ChildRegisterForm) session.getAttribute("save" + payRequest.getOrderId());

        if(response.statusCode() == HttpStatus.OK.value()){
            try {
                paymentService.save(changePayment(response, session, userSession.getId(), childInfo.getName()));

                Long gymId = childRegisterService.register(userSession.getId(), childInfo.getGymId(), createChild(childInfo));
                userSession.getGymIds().add(gymId);

                return ResponseEntity.ok("Payment successful");
            }catch (Exception e){
                log.debug("error: {}", e.getMessage());
                requestPaymentCancel(payRequest.getPaymentKey(), "결제 승인 후 저장 중 오류 발생. 결제가 취소되었습니다.");

                //TODO
                //취소된 결과 저장
                //취소 후 child 수정

                return ResponseEntity.badRequest().body(PaymentErrorResponse.builder().code(500).message("결제 승인 후 저장 중 오류 발생. 결제가 취소되었습니다.").build());
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
    }

    @GetMapping("/payments/{userId}")
    public String receipts(@PathVariable Long userId, Model model) {
        List<PayReceiptForm> receipts = paymentService.getReceipts(userId);

        model.addAttribute("receipts", receipts);

        return "pay/receipts";
    }

    @PostMapping("/gym/payment/cancel")
    public ResponseEntity paymentCancel(@RequestBody PayCancelDto payCancelDto,
                                @Login LoginUserSession userSession) throws IOException, InterruptedException {

        log.info("Cancel payment request {} ", payCancelDto);

        //결제 취소
        HttpResponse response = requestPaymentCancel(payCancelDto.getPaymentKey(), "고객요청");
        String status = checkStatus(response);

        if(status.equals("CANCELED")){

            paymentService.updatePayment(payCancelDto.getId(), getPayStatus(status));

            //성공하면 등록 취소
            changeChildAfterCancel(userSession.getId(), payCancelDto);
            Set<Long> myGymList = loginService.getMyGymList(userSession.getId());
            userSession.getGymIds().clear();
            userSession.getGymIds().addAll(myGymList);

            return ResponseEntity.ok("Payment cancel successful");
        }

        return ResponseEntity.badRequest().body("error");
    }

    public HttpResponse requestConfirm(PayRequest confirmPaymentRequest) throws IOException, InterruptedException {
        String tossOrderId = confirmPaymentRequest.getOrderId();
        String amount = String.valueOf(confirmPaymentRequest.getAmount());
        String tossPaymentKey = confirmPaymentRequest.getPaymentKey();

        // 승인 요청에 사용할 JSON 객체를 만듭니다.
        JsonNode requestObj = objectMapper.createObjectNode()
                .put("orderId", tossOrderId)
                .put("amount", amount)
                .put("paymentKey", tossPaymentKey);

        // ObjectMapper를 사용하여 JSON 객체를 문자열로 변환
        String requestBody = objectMapper.writeValueAsString(requestObj);

        // 결제 승인 API를 호출
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tossPaymentsConfig.url + "/confirm"))
                .header("Authorization", getAuthorizations())
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse requestPaymentCancel(String paymentKey, String cancelReason) throws IOException, InterruptedException {
        log.info("pay cancel {} ", paymentKey);

        JsonNode requestObj = objectMapper.createObjectNode()
                .put("cancelReason", cancelReason);

        String requestBody = objectMapper.writeValueAsString(requestObj);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tossPaymentsConfig.url + "/" + paymentKey + "/cancel"))
                .header("Authorization", getAuthorizations())
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String getAuthorizations() {
        String secretKey = tossPaymentsConfig.getSecretKey(); // 실제 Toss 시크릿 키로 변경
        String encodedAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedAuth;
    }

    public Payment changePayment(HttpResponse response, HttpSession session, Long id, String childName) throws JsonProcessingException {

        String responseBody = response.body().toString();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        String orderId = rootNode.path("orderId").asText();
        String orderName = rootNode.path("orderName").asText();
        long amount = rootNode.path("easyPay").path("amount").asLong();
        String paymentKey = rootNode.path("paymentKey").asText();
        String status = rootNode.path("status").asText();
        String requestedAt = rootNode.path("requestedAt").asText().substring(0, 19);
        String approvedAt = rootNode.path("approvedAt").asText().substring(0, 19);
        String method = rootNode.path("method").asText();

        Member customer = memberRepository.findById(id).orElse(null);

        session.removeAttribute("save" + orderId);

        return new Payment(orderId, orderName, amount, customer, paymentKey, getPayType(method),
                getPayStatus(status), LocalDateTime.parse(requestedAt), LocalDateTime.parse(approvedAt), childName);
    }

    public void changeChildAfterCancel(Long id, PayCancelDto payCancelDto) {
        if (payCancelDto.getOrderName().equals("체육관 등록")){
            childRegisterService.childRegisterCancel(id, payCancelDto.getChildName());
        }
    }

    public String checkStatus(HttpResponse response) throws JsonProcessingException {
        String result = response.body().toString();
        String status = objectMapper.readTree(result).path("status").asText();

        return status;
    }

    public PayType getPayType(String method) {
        return switch (method) {
            case "간편결제" -> PayType.QUICK;
            case "카드" -> PayType.CARD;
            case "현금" -> PayType.CASH;
            default -> throw new IllegalArgumentException("Invalid payment method: " + method);
        };
    }

    public PayStatus getPayStatus(String status) {
        return switch (status) {
            case "REDY" -> PayStatus.READY;
            case "IN_PROGRESS" -> PayStatus.IN_PROGRESS;
            case "DONE" -> PayStatus.DONE;
            case "CANCELED" -> PayStatus.CANCELED;
            case "ABORTED" -> PayStatus.ABORTED;
            case "EXPIRED" -> PayStatus.EXPIRED;
            default -> throw new IllegalArgumentException("Invalid payment status: " + status);
        };
    }
}
