package renewal.gym.dto.mypage;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;
import renewal.gym.controller.argument.PhoneNumberFormatter;
import renewal.gym.validator.groups.ValidationGroups;

@ToString
@Getter
public class MyGymForm {

    @NotNull(groups = ValidationGroups.NotEmptyGroup.class)
    private Long gymId;

    @NotBlank(message = "체육관 이름 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String gymName;

    @NotNull(message = "가격 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Min(value = 10000, message = "10,000원 ~ 1,000,000원 사이의 값을 입력해주세요.", groups = ValidationGroups.MinGroup.class)
    @Max(value = 1000000, message = "10,000원 ~ 1,000,000원 사이의 값을 입력해주세요.", groups = ValidationGroups.MaxGroup.class)
    private Integer price;

    @NotBlank(message = "체육관 전화번호 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "^(010\\d{8}|0[2-8][0-9]?\\d{7,8})$", message = "체육관 전화번호가 정확한지 확인해 주세요.", groups = ValidationGroups.PatternGroup.class)
    @PhoneNumberFormatter
    private String gymPhoneNum;

    private String zipCode;

    @NotBlank(message = "주소 : 필수 정보입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String roadName;

    private String detailAddress;

    @QueryProjection
    public MyGymForm(Long gymId, String gymName, Integer price, String gymPhoneNumber, String zipCode, String roadName, String detailAddress) {
        this.gymId = gymId;
        this.gymName = gymName;
        this.price = price;
        this.gymPhoneNum = gymPhoneNumber;
        this.zipCode = zipCode;
        this.roadName = roadName;
        this.detailAddress = detailAddress;
    }
}
