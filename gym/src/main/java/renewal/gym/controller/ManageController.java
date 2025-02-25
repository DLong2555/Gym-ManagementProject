package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.manage.EditChildForm;
import renewal.gym.dto.manage.EditChildResultForm;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.service.ManageService;
import renewal.gym.service.update.UpdateService;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/gym")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;
    private final UpdateService updateService;


    @GetMapping("/manage")
    public String manage(@Login LoginUserSession session, Model model) {

        Map<String, List<ParentsInfoForm>> childInMyGyms = manageService.findChildInMyGyms(session.getGymIds());

        log.debug("childInMyGys: {}", childInMyGyms.toString());

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

    @ResponseBody
    @PostMapping("/manage2")
    public Map<String, List<ParentsInfoForm>> manage1(@RequestBody List<Long> Ids) {

        return manageService.findChildInMyGyms2(Ids);
    }

}
