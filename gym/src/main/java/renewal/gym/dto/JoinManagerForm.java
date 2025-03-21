package renewal.gym.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class JoinManagerForm extends JoinForm {

    private String gymName;
    private Integer gymPrice;
    private String gymPhone;

    public JoinManagerForm(String memId, String password, String memName, String phone, String zipcode, String roadName, String detailAddress, String gymName, Integer gymPrice, String gymPhone) {
        super(memId, password, memName, phone, zipcode, roadName, detailAddress);
        this.gymName = gymName;
        this.gymPrice = gymPrice;
        this.gymPhone = gymPhone;
    }
}
