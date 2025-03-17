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
import renewal.gym.dto.manage.EditChildForm;
import renewal.gym.dto.manage.EditChildResultForm;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.service.GymService;
import renewal.gym.service.ManageService;
import renewal.gym.service.update.UpdateService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/gym")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;
    private final UpdateService updateService;
    private final GymService gymService;


    @GetMapping("/manage/{gymId}")
    public String manage(@PathVariable("gymId") Long gymId,
                         @Login LoginUserSession userSession, Model model) {

        List<GymInfoDto> gymNames = gymService.findGymNames(userSession.getGymIds());
        List<ParentsInfoForm> childInMyGyms = manageService.findChildInMyGyms(gymId);
//        List<GymInfoDto> gymNames = gymService.findGymNames(new HashSet<>(List.of(1L,2L)));
//        List<ParentsInfoForm> childInMyGyms = manageService.findChildInMyGyms(1L);

        log.debug("childInMyGys: {}", childInMyGyms.toString());

        model.addAttribute("gymNames", gymNames);
        model.addAttribute("childInMyGyms", childInMyGyms);

        return "gym/manageForm";
    }

    @ResponseBody
    @PostMapping("/manage")
    public EditChildResultForm editMember(@RequestBody List<EditChildForm> editChildForms, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return null;
        }

        EditChildResultForm result = new EditChildResultForm();
        for (EditChildForm editChildForm : editChildForms) {
            log.debug("editChildForm: {}", editChildForm.toString());

            if(updateService.updateChild(editChildForm)) result.getFailIds().add(editChildForm.getId());
            else result.getSuccessIds().add(editChildForm.getId());
        }

        return result;
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
