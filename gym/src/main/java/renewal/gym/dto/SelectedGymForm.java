package renewal.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectedGymForm {

    @NotBlank
    private String gymName;

    @NotBlank
    private String gymAddress;

}
