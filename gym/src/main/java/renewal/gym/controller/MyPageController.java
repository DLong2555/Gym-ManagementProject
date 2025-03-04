package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.domain.Role;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.mypage.*;
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

        if(session.getRole() == Role.USER){
            MyPageForm myPageForm = myPageService.getMyPageForm(session.getId());
            model.addAttribute("myPageForm", myPageForm);

            return "mypage/myPageForm";
        }else if(session.getRole() == Role.MANAGER){
            MyPageManagerForm myPageForm = myPageService.getMyManagerForm(session.getId());
            model.addAttribute("myPageForm", myPageForm);

            return "mypage/myPageManagerForm";
        }

        return "500";
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

        boolean result = updateService.updateUser(session.getLoginId(), myPageForm);

        if (result) {
            return null;
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ResponseBody
    @PostMapping("/manager/edit")
    public ResponseEntity managerEdit(@RequestBody @Validated(ValidationSequence.class) MyPageManagerForm myPageForm, BindingResult bindingResult,
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

        boolean result = updateService.updateManager(session.getLoginId(), myPageForm);

        if (result) {
            return null;
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/child")
    public String myChildForm(@Login LoginUserSession session, Model model) {

        List<MyChildForm> myChildForm = myPageService.getMyChildForm(session.getId());

        if (myChildForm == null) {
            return "500";
        }

        model.addAttribute("myChildForm", myChildForm);

        return "mypage/MyChildForm";
    }

    @ResponseBody
    @PostMapping("/child/edit")
    public ResponseEntity childEdit(@RequestBody @Validated(ValidationSequence.class) MyChildEditForm form, BindingResult bindingResult){
        log.debug("myChildEditForm: {}",form);

        if (bindingResult.hasErrors()) {
            log.debug("errors : {}", bindingResult.getAllErrors());

            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        boolean result = updateService.updateMyChild(form);

        if (result) {
            return null;
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/myGym")
    public String myGym(@Login LoginUserSession session, Model model) {

        List<MyGymForm> myGyms = myPageService.getMyGym(session.getId());
        model.addAttribute("myGyms", myGyms);

        for (MyGymForm myGym : myGyms) {
            log.debug("myGym: {}", myGym);
        }

        return "mypage/myGymForm";
    }

    @ResponseBody
    @PostMapping("/myGym/edit")
    public ResponseEntity myGymEdit(@RequestBody @Validated(ValidationSequence.class) MyGymForm form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.debug("errors : {}", bindingResult.getAllErrors());

            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        boolean result = updateService.updateMyGym(form);

        if(result) {
            return null;
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("type", "숫자만 입력 가능합니다.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
