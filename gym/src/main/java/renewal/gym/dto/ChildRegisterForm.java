package renewal.gym.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static renewal.gym.validator.groups.ValidationGroups.*;

@ToString
@Getter @Setter
public class ChildRegisterForm {

    private Long gymId;
    private String name;
    private String phone;
    private String age;
    private String gender;
    private String gymName;

    public ChildRegisterForm() {
    }

    public ChildRegisterForm(Long gymId, String gymName) {
        this.gymId = gymId;
        this.gymName = gymName;
    }
}
