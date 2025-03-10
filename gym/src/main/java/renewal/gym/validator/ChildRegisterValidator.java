package renewal.gym.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import renewal.gym.dto.register.ChildRegisterForm;

import java.util.List;
import java.util.Map;
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
            "name", "gender","phone", "gymName"
    );

    @Override
    public boolean supports(Class<?> clazz) {
        return ChildRegisterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("child validate start");

        ChildRegisterForm form = (ChildRegisterForm) target;

        if(!hasText(form.getAge())) errors.rejectValue("age", "required", "나이: 필수 정보입니다.");
        else{
            try {
                int age  = Integer.parseInt(form.getAge());
                if(age < 5 || age > 99) errors.rejectValue("age","range", "만 5세 이상부터 만 99세 이하만 등록 가능합니다.");
            }catch(NumberFormatException e){
                errors.rejectValue("age", "typeMismatch", "숫자만 입력가능합니다.");
            }
        }


//        if(form.getGymId() == null) errors.rejectValue("gymId", "required");

        for (String requiredField : requiredFields) {
            String value = getFieldValue(requiredField, form);

            if(!hasText(value)) errors.rejectValue(requiredField, "required", getDefaultMessageField(requiredField) + "필수 정보입니다.");
            else if(regexpMap.containsKey(requiredField) && !Pattern.matches(regexpMap.get(requiredField), value)) {
                errors.rejectValue(requiredField, "pattern", getDefaultMessageField(requiredField) + "옳바른 형식이 아닙니다.");
            }
        }
    }

    private String getFieldValue(String requiredField, ChildRegisterForm form) {
        return switch (requiredField) {
            case "name" -> form.getName();
            case "gender" -> form.getGender();
            case "phone" -> form.getPhone();
            case "gymName" -> form.getGymName();
            default -> null;
        };
    }

    private String getDefaultMessageField(String requiredField) {
        return switch (requiredField) {
            case "name" -> "이름: ";
            case "gender" -> "성별: ";
            case "phone" -> "휴대전화번호: ";
            case "gymName" -> "체육관 정보: ";
            default -> null;
        };
    }
}
