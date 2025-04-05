package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter
public class JoinForm {

    private String memId;
    private String password;
    private String name;

    private String phone;
    private String zipcode;
    private String roadName;
    private String detailAddress;

    public JoinForm() {
    }

    public JoinForm(String memId, String password, String name, String phone, String zipcode, String roadName, String detailAddress) {
        this.memId = memId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.zipcode = zipcode;
        this.roadName = roadName;
        this.detailAddress = detailAddress;
    }


}
