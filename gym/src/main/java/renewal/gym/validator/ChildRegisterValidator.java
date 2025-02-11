package renewal.gym.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import renewal.gym.dto.ChildRegisterForm;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
public class ChildRegisterValidator implements Validator {

    static private final Map<String, String> regexpMap = Map.of(
            "name", "^[가-힣]+$",
            "phone", "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$"
    );

    static private final List<String> requiredFields = List.of(
            "name", "gender","phone"
    );

    @Override
    public boolean supports(Class<?> clazz) {
        return ChildRegisterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("child validate start");

        ChildRegisterForm form = (ChildRegisterForm) target;

        if(form.getAge() == null && !errors.hasFieldErrors("age")) errors.rejectValue("age", "required");
        if(form.getGymId() == null) errors.rejectValue("gymId", "required");

        for (String requiredField : requiredFields) {
            String value = getFieldValue(requiredField, form);

            if(!hasText(value)) errors.rejectValue(requiredField, "required");
            else if(regexpMap.containsKey(requiredField) && !Pattern.matches(regexpMap.get(requiredField), value)) {
                errors.rejectValue(requiredField, "pattern");
            }
        }
    }

    private String getFieldValue(String requiredField, ChildRegisterForm form) {
        return switch (requiredField) {
            case "name" -> form.getName();
            case "gender" -> form.getGender();
            case "phone" -> form.getPhone();
            default -> null;
        };
    }
}
