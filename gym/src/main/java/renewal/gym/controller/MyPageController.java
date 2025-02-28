package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.mypage.myChildForm;
import renewal.gym.service.MyPageService;
import renewal.gym.service.update.UpdateService;
import renewal.gym.validator.groups.ValidationSequence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/gym/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final UpdateService updateService;


    @GetMapping
    public String myPage(@Login LoginUserSession session, Model model) {

        MyPageForm myPageForm = myPageService.getMyPageForm(session.getId());
        model.addAttribute("myPageForm", myPageForm);

        return "mypage/myPageForm";
    }

    @ResponseBody
    @PostMapping("/edit")
    public ResponseEntity edit(@RequestBody @Validated(ValidationSequence.class) MyPageForm myPageForm, BindingResult bindingResult,
                                  @Login LoginUserSession session) {

        log.debug("myPageForm: {}",myPageForm);

        if (bindingResult.hasErrors()) {
            log.debug("errors : {}", bindingResult.getAllErrors());

            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        boolean result = updateService.updateUser(session.getLoginId(), session.getRole(), myPageForm);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/child")
    public String myChildForm(@Login LoginUserSession session, Model model) {

        List<myChildForm> myChildForm = myPageService.getMyChildForm(session.getId());

        if (myChildForm == null) {
            return "500";
        }

        model.addAttribute("myChildForm", myChildForm);

        return "mypage/myChildForm";
    }
}
