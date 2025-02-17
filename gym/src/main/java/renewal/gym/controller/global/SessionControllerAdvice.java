package renewal.gym.controller.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.LoginUserSession;

@Slf4j
@ControllerAdvice
public class SessionControllerAdvice {

    @ModelAttribute
    public void addLoginUserToSession(@Login LoginUserSession session, Model model) {
        if (session != null) {
            log.debug("session: {}",session);
            model.addAttribute("loginUser", session);
        }
    }
}
