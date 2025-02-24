package renewal.gym.dto.manage;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter @Setter
public class EditChildForm {

    @NotNull
    private Long id;

    @NotNull
    private String belt;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
