package renewal.gym.controller;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import renewal.gym.constant.LoginSessionConst;
import renewal.gym.dto.LoginDTO;
import renewal.gym.dto.LoginForm;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.service.LoginService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/gym")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginFormView(LoginForm loginForm, Model model) {

        model.addAttribute("loginForm", loginForm);
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(value = "redirect", defaultValue = "/") String redirectUrl,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("errors: {}", bindingResult.getAllErrors());
            return "user/loginForm";
        }

        LoginUserSession loginMember = loginService.login(loginForm.getMemId(), loginForm.getPassword());
        if (loginMember == null) {
            bindingResult.reject("userLoginId", "아이디 또는 비밀번호를 확인해주세요.");
            return "user/loginForm";
        }

        request.getSession().setAttribute(LoginSessionConst.LoginSESSION_KEY, loginMember);
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) session.invalidate();

        return "redirect:/";
    }

    private final ManagerRepository managerRepository;

    @ResponseBody
    @PostMapping("/loginTest")
    public LoginDTO loginTest(@RequestParam String loginId) {
        return managerRepository.getLoginInfo(loginId);
    }

}
