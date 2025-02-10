package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import renewal.gym.constant.LoginSessionConst;
import renewal.gym.domain.Child;
import renewal.gym.dto.ChildRegisterForm;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.repository.ChildRepository;
import renewal.gym.service.child.ChildRegisterService;
import renewal.gym.validator.ChildRegisterValidator;

@Slf4j
@Controller
@RequestMapping("/gym/child")
@RequiredArgsConstructor
public class ChildRegisterController {

    private final ChildRegisterService childRegisterService;
    private final ChildRegisterValidator childRegisterValidator;

    @InitBinder("registerForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(childRegisterValidator);
    }

    @PostMapping("/form")
    public String selectGymRegister(@RequestParam("gymName") String gymName,
                                    @RequestParam("address") String address,
                                    Model model) {

        log.debug("gymName: {}", gymName);
        log.debug("address: {}", address);

        ChildRegisterForm childRegisterForm = new ChildRegisterForm();
        childRegisterForm.setGymName(gymName);
        childRegisterForm.setAddress(address);

        model.addAttribute("registerForm", childRegisterForm);

        return "child/childRegisterForm";
    }

    @PostMapping("/new")
    public String registerChild(@Validated @ModelAttribute("registerForm") ChildRegisterForm childRegisterForm, BindingResult bindingResult,
                                @SessionAttribute(name = LoginSessionConst.LoginSESSION_KEY, required = false) LoginUserSession session){

        log.debug("gymAddress: {}", childRegisterForm.getAddress());

        if(bindingResult.hasErrors()) {
            log.error("errors: {}", bindingResult.getAllErrors());
            return "child/childRegisterForm";
        }

        Long gymId = childRegisterService.register(session.getId(), createChild(childRegisterForm), childRegisterForm.getGymName(), childRegisterForm.getAddress());
        log.debug("gymId: {}", gymId);
        log.debug("session: {}", session);

        session.getGymIds().add(gymId);

        return "redirect:/";
    }

    private Child createChild(ChildRegisterForm childRegisterForm) {
        return new Child(childRegisterForm.getChildName(), childRegisterForm.getChildPhoneNum(),
                childRegisterForm.getChildAge(), childRegisterForm.getChildGender());
    }


}
