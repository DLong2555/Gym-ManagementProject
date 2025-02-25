package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.MyPageForm;
import renewal.gym.service.MyPageService;

@Slf4j
@Controller
@RequestMapping("/gym/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public String myPage(@Login LoginUserSession session, Model model) {

        MyPageForm myPageForm = myPageService.getMyPageForm(session.getId());
        model.addAttribute("myPageForm", myPageForm);

        return "mypage/myPageForm";
    }
}
