package renewal.gym.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import renewal.gym.dto.JoinForm;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JoinValidator implements Validator {

    static private final Map<String, String> regexpMap = Map.of(
            "memId", "^[a-z0-9]{5,20}$",
            "password", "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$",
            "name", "^[가-힣]+$",
            "phone", "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$"
    );

    static private final List<String> requiredFields = List.of(
            "memId", "password", "name",
            "phone", "zipcode"
    );

    @Override
    public boolean supports(Class<?> clazz) {
        // 검사하는 클래스 확인
        return JoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        JoinForm form = (JoinForm) target;

        for (String requiredField : requiredFields) {
            String value = getFieldValue(form, requiredField);

            if (!hasText(value) && !errors.hasFieldErrors(requiredField)) {
                errors.rejectValue(requiredField, "required");
            } else if (regexpMap.containsKey(requiredField) && !Pattern.matches(regexpMap.get(requiredField), value)) {
                errors.rejectValue(requiredField, "pattern");
            }
        }
    }

    private String getFieldValue(JoinForm form, String requiredField) {
        return switch (requiredField) {
            case "memId" -> form.getMemId();
            case "password" -> form.getPassword();
            case "name" -> form.getName();
            case "phone" -> form.getPhone();
            case "zipcode" -> form.getZipcode();
            default -> "";
        };
    }


}
