package renewal.gym.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import renewal.gym.dto.ChildRegisterForm;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
public class ChildRegisterValidator implements Validator {

    static Map<String, String> regexpMap = Map.of("name", "^[가-힣]+$", "phone", "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return ChildRegisterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("child validate start");

        ChildRegisterForm form = (ChildRegisterForm) target;

        // 이름 null 체크
        if(!hasText(form.getChildName())) errors.rejectValue("childName", "required");

        // 나이 null 체크
        if(form.getChildAge() == null && !errors.hasFieldErrors("childAge")) errors.rejectValue("childAge", "required");

        // 성별 null 체크
        if(!hasText(form.getChildGender())) errors.rejectValue("childGender", "required");

        // 체육관 이름 null 체크
        if(!hasText(form.getGymName())) errors.rejectValue("gymName", "required");

        // 이름 패턴 체크
        if(!errors.hasFieldErrors("childName") && patternCheck("name", form.getChildName())) errors.rejectValue("childName", "pattern.name");

        //전화번호 패턴 체크
        if(form.getChildPhoneNum() != null) {
            if (patternCheck("phone", form.getChildPhoneNum())) errors.rejectValue("childPhoneNum", "pattern.phone");
        }
    }

    public boolean patternCheck(String valName, String value){
        String regexp = regexpMap.get(valName);

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(value);

        return !matcher.matches();
    }
}
