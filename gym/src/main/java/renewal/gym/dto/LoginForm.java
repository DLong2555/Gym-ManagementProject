package renewal.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginForm {

    @NotBlank
    private String memId;

    @NotBlank
    private String password;
}
