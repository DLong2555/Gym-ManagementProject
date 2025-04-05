package renewal.gym.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.domain.Member;
import renewal.gym.dto.RegularPayInfo;
import renewal.gym.dto.RegularPaymentForm;
import renewal.gym.dto.register.ChildRegisterForm;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.SelectedGymForm;
import renewal.gym.dto.register.ParentInfoForm;
import renewal.gym.repository.MemberRepository;
import renewal.gym.service.GymService;
import renewal.gym.service.child.ChildService;
import renewal.gym.validator.ChildRegisterValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/gym/child")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;
    private final GymService gymService;
    private final ChildRegisterValidator childRegisterValidator;
    private final MemberRepository memberRepository;

    @InitBinder("registerForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(childRegisterValidator);
    }

    @PostMapping("/form")
    public String selectGymRegister(@ModelAttribute SelectedGymForm form, Model model,
                                    @Login LoginUserSession session) {

        log.debug("gymId: {}", form.getGymId());
        log.debug("gymName: {}", form.getGymName());

        Integer gymPrice = gymService.findGymPriceById(form.getGymId());
        ParentInfoForm parentInfo = childService.getParentInfo(session.getId());

        ChildRegisterForm childRegisterForm = new ChildRegisterForm(form.getGymId(), form.getGymName(), gymPrice);

        model.addAttribute("registerForm", childRegisterForm);
        model.addAttribute("parentInfo", parentInfo);

        return "child/childRegisterForm";
    }

    @ResponseBody
    @PostMapping("/check")
    public Map<String, String> registerChildCheck(@Validated @ModelAttribute("registerForm") ChildRegisterForm childRegisterForm, BindingResult bindingResult,
                                             @Login LoginUserSession session, HttpSession saveData) {

        log.debug("gymAddress: {}", childRegisterForm);

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            log.debug("fieldError: {}", errors);
            return errors;
        }

        boolean duplication = childService.duplicationCheck(childRegisterForm.getGymId(), session.getId(), childRegisterForm.getName());

        if(duplication) {
            return Map.of("name", "이미 등록되어있습니다.");
        }

        saveData.setAttribute("save" + childRegisterForm.getOrderId(), childRegisterForm);

        return Map.of("success", "success");
    }

    @GetMapping("/regular")
    public String regularPaymentForm(@Login LoginUserSession userSession, Model model) {

        List<RegularPaymentForm> myRegularPayment = childService.getMyRegularPayment(userSession.getId());

        Member member = memberRepository.findById(userSession.getId()).orElse(null);
        if (member != null) {
            model.addAttribute("memName", member.getMemName());
            model.addAttribute("phoneNumber", member.getMemPhoneNum());
        }

        model.addAttribute("myRegularPayment", myRegularPayment);

        return "child/regularPaymentForm";
    }

    @PostMapping("/pay/check")
    public ResponseEntity check(@RequestBody RegularPayInfo regularPayInfo, HttpSession session) {
        log.info("Check child: {}", regularPayInfo);
        session.setAttribute("save" + regularPayInfo.getOrderId(), regularPayInfo);

        return ResponseEntity.ok("success");
    }

}
