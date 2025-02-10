//package renewal.gym.validator;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.validation.BindException;
//import org.springframework.validation.Errors;
//import renewal.gym.dto.JoinForm;
//
//import java.util.Objects;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//class JoinValidatorTest {
//
//    private JoinValidator joinValidator;
//
//    @BeforeEach
//    void setUp(){
//        joinValidator = new JoinValidator();
//    }
//
//    @Test
//    @DisplayName("조건 충족 시 에러 발생 테스트")
//    void validJoinFormTest(){
//        JoinForm joinForm = new JoinForm("maxol2558","qwerty1234!","홍길동",
//                "홍홍", "01012345678",
//                "12345", "천안시 청당동 21-12길","빌리지 111호");
//
//        Errors errors = new BindException(joinForm, "joinForm");
//
//        joinValidator.validate(joinForm, errors);
//
//        assertFalse(errors.hasErrors());
//    }
//
//    @Test
//    @DisplayName("조건 미충족 시 에러 발생 테스트")
//    void invalidJoinFormTest(){
//        JoinForm joinForm = new JoinForm();
//
//        Errors errors = new BindException(joinForm, "joinForm");
//
//        joinValidator.validate(joinForm, errors);
//
//        assertTrue(errors.hasErrors());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("memId")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("password")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("memName")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("memNick")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("memPhoneNum")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("zipcode")).getCode());
//        assertEquals("required", Objects.requireNonNull(errors.getFieldError("roadName")).getCode());
//    }
//
//    @Test
//    @DisplayName("패턴 체크 통과 메서드 테스트")
//    void patternChkWithValidValueTest(){
//
//        assertFalse(joinValidator.patternCheck("id", "maxol2558"));
//        assertFalse(joinValidator.patternCheck("pwd", "qwerty1234!"));
//        assertFalse(joinValidator.patternCheck("name", "홍길동"));
//        assertFalse(joinValidator.patternCheck("nick", "홍홍"));
//        assertFalse(joinValidator.patternCheck("phone", "01012345678"));
//
//    }
//
//    @Test
//    @DisplayName("패턴 체크 실패 테스트 및 에러 발생 테스트")
//    void patternChkWithInvalidValueTest(){
//        JoinForm joinForm = new JoinForm("m","qwerty1234","asd",
//                "홍", "11012678",
//                "12345", "천안시 청당동 21-12길","빌리지 111호");
//
//        Errors errors = new BindException(joinForm, "joinForm");
//
//        joinValidator.validate(joinForm, errors);
//
//        assertTrue(joinValidator.patternCheck("id", "m"));
//        assertTrue(joinValidator.patternCheck("pwd", "qwerty1234"));
//        assertTrue(joinValidator.patternCheck("name", "asd"));
//        assertTrue(joinValidator.patternCheck("nick", "홍"));
//        assertTrue(joinValidator.patternCheck("phone", "11012678"));
//
//        assertEquals("pattern.id", Objects.requireNonNull(errors.getFieldError("memId")).getCode());
//        assertEquals("pattern.pwd", Objects.requireNonNull(errors.getFieldError("password")).getCode());
//        assertEquals("pattern.name", Objects.requireNonNull(errors.getFieldError("memName")).getCode());
//        assertEquals("pattern.nick", Objects.requireNonNull(errors.getFieldError("memNick")).getCode());
//        assertEquals("pattern.phone", Objects.requireNonNull(errors.getFieldError("memPhoneNum")).getCode());
//    }
//
//}