package renewal.gym.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JoinManagerValidatorTest {

//    private JoinManagerValidator validator;
//
//    @BeforeEach
//    void setUp() {
//        validator = new JoinManagerValidator();
//    }
//
//    @Test
//    @DisplayName("조건 충족 시 에러 발생 테스트")
//    void validJoinFormTest(){
//        JoinManagerForm joinForm = new JoinManagerForm("gkxkzp2558","qwerty1234@#","김연수", "01062017834", "12345", "천안시 청당동 21-12길","빌리지 111호",
//                "아우내 합기도", 130000, "01021421234");
//
//        Errors errors = new BindException(joinForm, "joinForm");
//
//        validator.validate(joinForm, errors);
//
//        assertFalse(errors.hasErrors());
//    }
//
//    @Test
//    @DisplayName("조건 미충족 시 에러 발생 테스트")
//    void invalidJoinFormTest(){
//        JoinManagerForm joinForm = new JoinManagerForm();
//
//        Errors errors = new BindException(joinForm, "joinForm");
//
//        validator.validate(joinForm, errors);
//
//        assertTrue(errors.hasErrors());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("memId")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("password")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("memName")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("memPhoneNum")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("zipcode")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("roadName")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("gymName")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("gymPrice")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("gymPhone")).getCode());
//    }
//
//    @Test
//    @DisplayName("패턴 체크 실패 테스트 및 에러 발생 테스트")
//    void patternChkWithInvalidValueTest(){
//        JoinManagerForm joinForm = new JoinManagerForm("m","qwerty1234","asd", "11012678", "12345", "천안시 청당동 21-12길","빌리지 111호",
//                "아우내 합기도", 130000, "0102142");
//
//        Errors errors = new BindException(joinForm, "joinForm");
//
//        validator.validate(joinForm, errors);
//
//        assertTrue(validator.patternCheck("id", "m"));
//        assertTrue(validator.patternCheck("pwd", "qwerty1234"));
//        assertTrue(validator.patternCheck("name", "asd"));
//        assertTrue(validator.patternCheck("phone", "11012678"));
//
//        assertEquals("pattern.id", Objects.requireNonNull(errors.getFieldError("memId")).getCode());
//        assertEquals("pattern.pwd", Objects.requireNonNull(errors.getFieldError("password")).getCode());
//        assertEquals("pattern.name", Objects.requireNonNull(errors.getFieldError("memName")).getCode());
//        assertEquals("pattern.phone", Objects.requireNonNull(errors.getFieldError("memPhoneNum")).getCode());
//    }

}