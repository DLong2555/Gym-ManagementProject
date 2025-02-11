package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import renewal.gym.domain.*;
import renewal.gym.dto.JoinForm;
import renewal.gym.dto.JoinManagerForm;
import renewal.gym.service.JoinService;
import renewal.gym.validator.JoinManagerValidator;
import renewal.gym.validator.JoinValidator;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/gym")
public class JoinController {

    private final JoinService JoinService;
    private final JoinValidator joinValidator;
    private final JoinManagerValidator joinManagerValidator;

    @InitBinder("joinForm")
    public void initBinderUser(WebDataBinder binder) {
        binder.addValidators(joinValidator);
    }

    @InitBinder("joinManagerForm")
    public void initBinderManager(WebDataBinder binder) {
        binder.addValidators(joinManagerValidator);
    }

    @GetMapping("/joinSelect")
    public String joinSelect() {
        return "user/joinSelect";
    }

    @GetMapping("/join/{role}")
    public String joinFormView(@PathVariable("role") String role,  Model model) {

        if(role.equals("user")) {
            model.addAttribute("joinForm", new JoinForm());
            return "user/joinForm";
        } else if(role.equals("manager")) {
            model.addAttribute("joinManagerForm", new JoinManagerForm());
            return "manager/joinManagerForm";
        }

        return "redirect:/";
    }

    @PostMapping("/join/user")
    public String join(@Validated @ModelAttribute("joinForm") JoinForm joinForm, BindingResult bindingResult) {

        if (JoinService.duplicateMemberId(joinForm.getMemId())) {
            bindingResult.rejectValue("memId", "duplicate.id");
        }

        if(bindingResult.hasErrors()){
            log.error("errors: {}", bindingResult.getAllErrors());
            return "user/joinForm";
        }

        JoinService.join(createUser(joinForm));

        return "redirect:/gym/login";
    }

    @PostMapping("/join/manager")
    public String joinManger(@Validated @ModelAttribute("joinManagerForm") JoinManagerForm joinForm, BindingResult bindingResult) {

        if (JoinService.duplicateMemberId(joinForm.getMemId())) {
            bindingResult.rejectValue("memId", "duplicate.id");
        }

        if(bindingResult.hasErrors()){
            log.error("errors: {}", bindingResult.getAllErrors());
            return "manager/joinManagerForm";
        }

        JoinService.joinManger(createManagerAndGym(joinForm));

        return "redirect:/gym/login";
    }

    public Member createUser(JoinForm joinForm){

        String password = joinForm.getPassword();

        Address address = new Address(joinForm.getZipcode(), joinForm.getRoadName(), joinForm.getDetailAddress());

        return new Member(joinForm.getMemId(), joinForm.getPassword(), joinForm.getName(), joinForm.getPhone(), address);
    }

    public Gym createManagerAndGym(JoinManagerForm joinForm){

        Address address = new Address(joinForm.getZipcode(), joinForm.getRoadName(), joinForm.getDetailAddress());
        Manager manager = new Manager(joinForm.getMemId(), joinForm.getPassword(), joinForm.getName(), joinForm.getPhone());

        return new Gym(joinForm.getGymName(), joinForm.getGymPrice(), joinForm.getGymPhone(),address,manager);
    }

}
