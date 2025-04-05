package renewal.gym.controller;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.GymInfoDto;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.event.ManageEventForm;
import renewal.gym.dto.manage.EditChildForm;
import renewal.gym.dto.manage.EditChildResultForm;
import renewal.gym.dto.manage.EventParticipantForm;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.service.EventService;
import renewal.gym.service.GymService;
import renewal.gym.service.ManageService;
import renewal.gym.service.update.UpdateService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/gym/manager")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;
    private final UpdateService updateService;
    private final GymService gymService;
    private final EventService eventService;


    @GetMapping("/manage")
    public String manage(@RequestParam(value = "gymId") String encryptedGymId,
                         @RequestParam(value = "ctg", required = false) String ctg,
                         HttpServletRequest request,
                         @Login LoginUserSession userSession, Model model) {

        Long gymId = (Long) request.getAttribute("gymId");

        List<GymInfoDto> gymNames = gymService.findGymNames(userSession.getId());
        List<ParentsInfoForm> childInMyGyms = manageService.findChildInMyGyms(gymId, ctg);

        log.debug("childInMyGys: {}", childInMyGyms.toString());

        model.addAttribute("ctg", ctg);
        model.addAttribute("gymNames", gymNames);
        model.addAttribute("childInMyGyms", childInMyGyms);

        return "gym/manageForm";
    }

    @ResponseBody
    @PostMapping("/manage")
    public EditChildResultForm editMember(@RequestBody List<EditChildForm> editChildForms, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return null;
        }

        EditChildResultForm result = new EditChildResultForm();
        for (EditChildForm editChildForm : editChildForms) {
            log.debug("editChildForm: {}", editChildForm.toString());

            if (updateService.updateChild(editChildForm)) result.getFailIds().add(editChildForm.getId());
            else result.getSuccessIds().add(editChildForm.getId());
        }

        return result;
    }

    @GetMapping("/manage/{gymId}/delete")
    public String delete(@PathVariable("gymId") String encryptedGymId,
                         @RequestParam("childId") Long childId) {

        updateService.deleteGymFromChild(childId);

        return "redirect:/gym/manager/manage?gymId=" + encryptedGymId + "&ctg=expired";
    }

    @GetMapping("/manage/event")
    public String eventListForm(@RequestParam(value = "gymId") String encryptedGymId,
                                @RequestParam(value = "eventId", required = false) Long eventId,
                                HttpServletRequest request,
                                @Login LoginUserSession userSession, Model model) {

        Long gymId = (Long) request.getAttribute("gymId");

        if (eventId != null) {
            List<EventParticipantForm> participants = manageService.getParticipants(eventId);
            model.addAttribute("participants", participants);
        }

        List<GymInfoDto> gymNames = gymService.findGymNames(userSession.getId());

        List<ManageEventForm> events = eventService.getEvents(gymId);
        model.addAttribute("gymId", encryptedGymId);

        model.addAttribute("eventList", events);
        model.addAttribute("gymNames", gymNames);

        return "event/eventListForm";
    }
    

}
