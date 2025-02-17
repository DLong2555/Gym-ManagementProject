package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.ChildInfoForm;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.service.ManageService;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/gym")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;

    @GetMapping("/manage")
    public Map<String, Map<String, List<ChildInfoForm>>> manage(@Login LoginUserSession session, Model model) {

        Map<String, Map<String, List<ChildInfoForm>>> childInMyGyms = manageService.findChildInMyGyms(session.getGymIds());

        log.debug("childInMyGys: {}", childInMyGyms.toString());

        model.addAttribute("childInMyGyms", childInMyGyms);

        return childInMyGyms;
    }
}
