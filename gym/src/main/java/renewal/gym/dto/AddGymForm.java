package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGymForm {

    private String gymName;
    private Integer gymPrice;
    private String gymPhone;
    private String zipcode;
    private String roadName;
    private String detailAddress;
}
