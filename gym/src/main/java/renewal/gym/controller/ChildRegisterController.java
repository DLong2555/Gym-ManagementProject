package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.domain.Child;
import renewal.gym.dto.register.ChildRegisterForm;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.SelectedGymForm;
import renewal.gym.dto.register.ParentInfoForm;
import renewal.gym.repository.MemberRepository;
import renewal.gym.service.GymService;
import renewal.gym.service.child.ChildRegisterService;
import renewal.gym.validator.ChildRegisterValidator;

import java.util.HashMap;
import java.util.Map;

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
    public String selectGymRegister(@ModelAttribute SelectedGymForm form, Model model,
                                    @Login LoginUserSession session) {

        log.debug("gymId: {}", form.getGymId());
        log.debug("gymName: {}", form.getGymName());

        Integer gymPrice = gymService.findGymPriceById(form.getGymId());
        ParentInfoForm parentInfo = childRegisterService.getParentInfo(session.getId());

        ChildRegisterForm childRegisterForm = new ChildRegisterForm(form.getGymId(), form.getGymName(), gymPrice);

        model.addAttribute("registerForm", childRegisterForm);
        model.addAttribute("parentInfo", parentInfo);

        return "child/childRegisterForm";
    }

    @ResponseBody
    @PostMapping("/new")
    public Map<String, String> registerChild(@Validated @ModelAttribute("registerForm") ChildRegisterForm childRegisterForm, BindingResult bindingResult,
                                             @Login LoginUserSession session){

        log.debug("gymAddress: {}", childRegisterForm);

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            log.debug("fieldError: {}", errors);
            return errors;
        }

        Long gymId = childRegisterService.register(session.getId(), childRegisterForm.getGymId(), createChild(childRegisterForm));

        if(gymId == null) {
            return Map.of("name", "이미 등록되어있습니다.");
        }

        session.getGymIds().add(gymId);
        log.debug("session: {}", session);

        return Map.of("success", "success");
    }

    private Child createChild(ChildRegisterForm childRegisterForm) {

        return new Child(childRegisterForm.getName(), childRegisterForm.getPhone(),
                Integer.parseInt(childRegisterForm.getAge()), childRegisterForm.getGender());
    }


}
