package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class ChildRegisterForm {

    private Long gymId;
    private String name;
    private String phone;

    private Integer age;
    private String gender;

    private String gymName;

    public ChildRegisterForm() {
    }

    public ChildRegisterForm(Long gymId, String gymName) {
        this.gymId = gymId;
        this.gymName = gymName;
    }
}
