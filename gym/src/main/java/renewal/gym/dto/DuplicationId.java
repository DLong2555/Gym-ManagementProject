package renewal.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import renewal.gym.validator.groups.ValidationGroups;

import static renewal.gym.validator.groups.ValidationGroups.*;

@Getter @Setter
public class DuplicationId {

    @NotBlank(message = "아이디는 비어있을 수 없습니다.", groups = NotEmptyGroup.class)
    @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.", groups = PatternGroup.class)
    private String memId;

}
