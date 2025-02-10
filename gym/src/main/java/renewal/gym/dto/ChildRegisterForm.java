package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChildRegisterForm {

    private String childName;

    private String childPhoneNum;

    private Integer childAge;
    private String childGender;

    private String gymName;
    private String address;
}
