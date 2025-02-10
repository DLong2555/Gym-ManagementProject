package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinManagerForm extends JoinForm {

    private String gymName;
    private Integer gymPrice;
    private String gymPhone;
}
