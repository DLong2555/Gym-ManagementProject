package renewal.gym.controller.global;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.LoginUserSession;

@ControllerAdvice
public class SessionControllerAdvice {

    @ModelAttribute
    public void addLoginUserToSession(@Login LoginUserSession session, Model model) {
        if (session != null) {
            model.addAttribute("loginUser", session);
        }
    }
}
