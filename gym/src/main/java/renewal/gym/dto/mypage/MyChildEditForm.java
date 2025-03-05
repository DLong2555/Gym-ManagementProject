package renewal.gym.dto.mypage;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;
import renewal.gym.validator.groups.ValidationGroups;

@ToString
@Getter
public class MyChildEditForm {

    @NotNull(groups = ValidationGroups.NotEmptyGroup.class)
    private Long id;

    @NotBlank(message = "이름 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "^[가-힣]+$", message = "한글 이름만 가능합니다",groups = ValidationGroups.PatternGroup.class)
    private String name;

    @NotNull(message = "나이 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Min(value = 5, message = "만 5세 ~ 만 99세 사이의 나이만 입력 가능합니다.", groups = ValidationGroups.MinGroup.class)
    @Max(value = 99, message = "만 5세 ~ 만 99세 사이의 나이만 입력 가능합니다.", groups = ValidationGroups.MaxGroup.class)
    private Integer age;

    @NotBlank(message = "성별 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String gender;

    @NotBlank(message = "전화번호 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "휴대전화번호가 정확한지 확인해 주세요.", groups = ValidationGroups.PatternGroup.class)
    private String phoneNumber;
}
