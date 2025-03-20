package renewal.gym.controller;

import com.querydsl.core.Tuple;
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
    public String manage(@RequestParam(value = "gymId") Long gymId,
                         @RequestParam(value = "ctg", required = false) String ctg,
                         @Login LoginUserSession userSession, Model model) {

        List<GymInfoDto> gymNames = gymService.findGymNames(userSession.getGymIds());
        List<ParentsInfoForm> childInMyGyms = manageService.findChildInMyGyms(gymId, ctg);
//        List<GymInfoDto> gymNames = gymService.findGymNames(new HashSet<>(List.of(1L,2L)));
//        List<ParentsInfoForm> childInMyGyms = manageService.findChildInMyGyms(1L);

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
    public String delete(@PathVariable("gymId") Long gymId,
                         @RequestParam("childId") Long childId) {

        updateService.deleteGymFromChild(childId);

        return "redirect:/gym/manager/manage?gymId=" + gymId + "&ctg=expired";
    }

    @GetMapping("/manage/event")
    public String eventListForm(@RequestParam(value = "gymId", required = false) Long gymId,
                                @RequestParam(value = "eventId", required = false) Long eventId,
                                @Login LoginUserSession userSession, Model model) {

        if (eventId != null) {
            List<EventParticipantForm> participants = manageService.getParticipants(eventId);
            model.addAttribute("participants", participants);
        }

        List<GymInfoDto> gymNames = gymService.findGymNames(userSession.getGymIds());
        List<ManageEventForm> events;

        if (gymId != null) {
            events = eventService.getEvents(gymId);
            model.addAttribute("gymId", gymId);
        }else{
            events = eventService.getEvents(gymNames.get(0).getId());
            model.addAttribute("gymId", gymNames.get(0).getId());
        }

        model.addAttribute("eventList", events);
        model.addAttribute("gymNames", gymNames);

        return "event/eventListForm";
    }

//    @GetMapping("/manage/")
//    public String manage(@Login LoginUserSession session, Model model) {
//
////        Map<String, List<ParentsInfoForm>> childInMyGyms = manageService.findChildInMyGyms(session.getGymIds());
//        List<ParentsInfoForm> childInMyGyms = manageService.findChildInMyGyms();
//
//        log.debug("childInMyGys: {}", childInMyGyms.toString());
//
//        model.addAttribute("childInMyGyms", childInMyGyms);
//
//        return "gym/manageForm";
//    }

//    @ResponseBody
//    @PostMapping("/manage2")
//    public List<ParentsInfoForm> manage1() {
//
//        return manageService.findChildInMyGyms2(1L);
//    }

}
