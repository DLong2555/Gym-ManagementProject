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
import renewal.gym.dto.SelectedGymForm;
import renewal.gym.repository.ChildRepository;
import renewal.gym.service.GymService;
import renewal.gym.service.child.ChildRegisterService;
import renewal.gym.validator.ChildRegisterValidator;

@Slf4j
@Controller
@RequestMapping("/gym/child")
@RequiredArgsConstructor
public class ChildRegisterController {

    private final ChildRegisterService childRegisterService;
    private final GymService gymService;
    private final ChildRegisterValidator childRegisterValidator;

    @InitBinder("registerForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(childRegisterValidator);
    }

    @PostMapping("/form")
    public String selectGymRegister(@Validated @ModelAttribute SelectedGymForm form, BindingResult bindingResult, Model model) {

        log.debug("gymId: {}", form.getGymId());
        log.debug("gymName: {}", form.getGymName());


        if (bindingResult.hasErrors()) {
            log.debug("errors: {}", bindingResult.getAllErrors());
            return "home";
        }

        ChildRegisterForm childRegisterForm = new ChildRegisterForm(form.getGymId(), form.getGymName());

        model.addAttribute("registerForm", childRegisterForm);

        return "child/childRegisterForm";
    }

    @PostMapping("/new")
    public String registerChild(@Validated @ModelAttribute("registerForm") ChildRegisterForm childRegisterForm, BindingResult bindingResult,
                                @SessionAttribute(name = LoginSessionConst.LoginSESSION_KEY, required = false) LoginUserSession session){

        log.debug("gymAddress: {}", childRegisterForm);

        if(bindingResult.hasErrors()) {
            log.error("errors: {}", bindingResult.getAllErrors());
            return "child/childRegisterForm";
        }

        Long gymId = childRegisterService.register(session.getId(), childRegisterForm.getGymId(), createChild(childRegisterForm));

        session.getGymIds().add(gymId);
        log.debug("session: {}", session);

        return "redirect:/";
    }

    private Child createChild(ChildRegisterForm childRegisterForm) {

        return new Child(childRegisterForm.getName(), childRegisterForm.getPhone(),
                childRegisterForm.getAge(), childRegisterForm.getGender());
    }


}
