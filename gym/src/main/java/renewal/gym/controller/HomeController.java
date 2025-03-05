package renewal.gym.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import renewal.gym.dto.pay.SavePayAmount;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String index(){

        return "home";
    }

    @GetMapping("/test")
    public String test(){

        return "pay/payForm";
    }

    @GetMapping("/success")
    public String test1(){

        return "pay/success";
    }

    @GetMapping("/fail")
    public String fail(){

        return "pay/fail";
    }

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
               "message", "결제 금액 정보가 유효하지 않습니다.",
               "code", "INVALID_AMOUNT"
            ));
        }

        log.info("Check pay amount successful");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Amount is valid"));
    }

}
