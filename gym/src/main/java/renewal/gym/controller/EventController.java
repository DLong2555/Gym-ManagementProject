package renewal.gym.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.domain.Member;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.dto.event.EventPayForm;
import renewal.gym.dto.event.MyChildNames;
import renewal.gym.repository.MemberRepository;
import renewal.gym.service.EventService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/gym")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final MemberRepository memberRepository;

    @GetMapping("/event/{boardId}")
    public String event(@PathVariable(value = "boardId") Long boardId,
                        @Login LoginUserSession userSession, Model model) {

        EventInfoForm eventInfo = eventService.getEventInfo(boardId);
        eventInfo.setId(boardId);

        List<MyChildNames> childNames = eventService.getChildNames(userSession.getId(), eventInfo.getGymId(), boardId);

        Member member = memberRepository.findById(userSession.getId()).orElse(null);
        if (member != null) {
            model.addAttribute("memName", member.getMemName());
            model.addAttribute("phoneNumber", member.getMemPhoneNum());
        }

        model.addAttribute("eventInfo", eventInfo);
        model.addAttribute("childNames", childNames);

        return "event/eventApplication";
    }

    @PostMapping("/event/check")
    public ResponseEntity check(@RequestBody EventPayForm form, HttpSession session) {
        log.info("Check event: {}", form);
        session.setAttribute("save" + form.getOrderId(), form);

        return ResponseEntity.ok("success");
    }


}
