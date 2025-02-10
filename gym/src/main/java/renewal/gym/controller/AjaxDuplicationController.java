package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import renewal.gym.dto.DuplicationId;
import renewal.gym.service.JoinService;
import renewal.gym.validator.groups.ValidationSequence;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/gym")
@RequiredArgsConstructor
public class AjaxDuplicationController {

    private final JoinService joinService;

    @PostMapping("/duplicationIdCheck")
    public Map<String, Object> duplicationCheck(@Validated(ValidationSequence.class) @RequestBody DuplicationId request, BindingResult bindingResult) {
        log.debug("request: {}", request.getMemId());

        if(bindingResult.hasErrors()) {
            log.debug("bindingResult: {}", bindingResult.getAllErrors());
            return Map.of("errors", bindingResult.getFieldError().getDefaultMessage());
        }

        return Map.of("result", joinService.duplicateMemberId(request.getMemId()));
    }
}
