package renewal.gym.dto.mypage;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.controller.argument.PhoneNumberFormatter;
import renewal.gym.validator.groups.ValidationGroups;

@ToString
@Getter @Setter
public class MyPageForm {

    @NotBlank(message = "이름 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "^[가-힣]+$", message = "한글 이름만 가능합니다",groups = ValidationGroups.PatternGroup.class)
    private String name;

    @NotBlank(message = "전화번호 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "휴대전화번호가 정확한지 확인해 주세요.", groups = ValidationGroups.PatternGroup.class)
    @PhoneNumberFormatter
    private String phoneNumber;

    private String zipCode;

    @NotBlank(message = "주소 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String roadName;

    private String detailAddress;

    @QueryProjection
    public MyPageForm(String name, String phoneNumber, String zipCode, String roadName, String detailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.roadName = roadName;
        this.detailAddress = detailAddress;
    }

}
