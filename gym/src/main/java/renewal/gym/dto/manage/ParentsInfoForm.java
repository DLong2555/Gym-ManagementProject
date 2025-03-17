package renewal.gym.dto.manage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.controller.argument.PhoneNumberFormatter;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
public class ParentsInfoForm {

    private Long id;
    private String memName;

    @PhoneNumberFormatter
    private String memPhoneNum;
    private String roadName;
    private String detailAddress;

    private List<ChildInfoForm> children = new ArrayList<>();

    public ParentsInfoForm(Long id, String memName, String memPhoneNum, String roadName, String detailAddress) {
        this.id = id;
        this.memName = memName;
        this.memPhoneNum = memPhoneNum;
        this.roadName = roadName;
        this.detailAddress = detailAddress;
    }

    public ParentsInfoForm(String memName, String memPhoneNum, String roadName, String detailAddress) {
        this.memName = memName;
        this.memPhoneNum = memPhoneNum;
        this.roadName = roadName;
        this.detailAddress = detailAddress;
    }
}
