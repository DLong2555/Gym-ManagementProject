package renewal.gym.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import renewal.gym.dto.JoinManagerForm;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
public class JoinManagerValidator implements Validator {

    static private final Map<String, String> regexpMap = Map.of(
            "memId", "^[a-z0-9]{5,20}$",
            "password", "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$",
            "name", "^[가-힣]+$",
            "phone", "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
            "gymPhone", "^0\\d{1,2}-?\\d{3,4}-?\\d{4}$"
    );

    static private final List<String> requiredFields = List.of(
            "memId", "password", "name",
            "phone", "zipcode",
            "gymName", "gymPhone"
    );

    static private final List<String> requiredIntegerFields = List.of(
            "gymPrice"
    );

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("supports: {}", clazz);
        return JoinManagerForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        JoinManagerForm form = (JoinManagerForm) target;

        for (String requiredField : requiredIntegerFields) {
            Integer value = getIntegerFieldValue(form, requiredField);

            if (value == null && !errors.hasFieldErrors(requiredField)) {
                errors.rejectValue(requiredField, "required");
            }
        }

        for (String requiredField : requiredFields) {
            String value = getFieldValue(form, requiredField);

            if (!hasText(value) && !errors.hasFieldErrors(requiredField)) {
                errors.rejectValue(requiredField, "required");
            } else if (regexpMap.containsKey(requiredField) && !Pattern.matches(regexpMap.get(requiredField), value)) {
                errors.rejectValue(requiredField, "pattern");
            }
        }
    }

    private String getFieldValue(JoinManagerForm form, String requiredField) {
        return switch (requiredField) {
            case "memId" -> form.getMemId();
            case "password" -> form.getPassword();
            case "name" -> form.getName();
            case "phone" -> form.getPhone();
            case "zipcode" -> form.getZipcode();
            case "gymName" -> form.getGymName();
            case "gymPhone" -> form.getGymPhone();
            default -> "";
        };
    }

    private Integer getIntegerFieldValue(JoinManagerForm form, String requiredField) {
        return switch (requiredField) {
            case "gymPrice" -> form.getGymPrice();
            default -> null;
        };
    }

}
