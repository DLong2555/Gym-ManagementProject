package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.dto.event.MyChildNames;
import renewal.gym.service.EventService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/gym/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/{boardId}")
    public String event(@PathVariable(value = "boardId") Long boardId,
                        @Login LoginUserSession userSession, Model model) {

        EventInfoForm eventInfo = eventService.getEventInfo(1L);
        eventInfo.setId(boardId);

        List<MyChildNames> childNames = eventService.getChildNames(userSession.getId(), eventInfo.getGymId());
        log.info("eventInfo: {}", eventInfo);
        log.info("childNames: {}", childNames);

        model.addAttribute("eventInfo", eventInfo);
        model.addAttribute("childNames", childNames);

        return "event/eventApplication";
    }

}
