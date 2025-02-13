package renewal.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectedGymForm {

    @NotNull
    private Long gymId;

    @NotBlank
    private String gymName;

}
