package renewal.gym.dto;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinForm {

    private String memId;
    private String password;
    private String memName;

    private String memPhoneNum;
    private String zipcode;
    private String roadName;
    private String detailAddress;

    public JoinForm() {
    }

    public JoinForm(String memId, String password, String memName, String memPhoneNum, String zipcode, String roadName, String detailAddress, String gymName, Integer gymPrice, String gymPhone) {
        this.memId = memId;
        this.password = password;
        this.memName = memName;
        this.memPhoneNum = memPhoneNum;
        this.zipcode = zipcode;
        this.roadName = roadName;
        this.detailAddress = detailAddress;
    }

    //    public JoinForm(String memId, String password, String memName, String memPhoneNum, String zipcode, String roadName, String detailAddress) {
//        this.memId = memId;
//        this.password = password;
//        this.memName = memName;
//        this.memPhoneNum = memPhoneNum;
//        this.zipcode = zipcode;
//        this.roadName = roadName;
//        this.detailAddress = detailAddress;
//    }
//
//    public JoinForm(String memId, String password, String memName, String memNick, String memPhoneNum, String zipcode, String roadName, String detailAddress) {
//        this.memId = memId;
//        this.password = password;
//        this.memName = memName;
//        this.memNick = memNick;
//        this.memPhoneNum = memPhoneNum;
//        this.zipcode = zipcode;
//        this.roadName = roadName;
//        this.detailAddress = detailAddress;
//    }
}
