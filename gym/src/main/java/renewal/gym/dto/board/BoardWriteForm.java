package renewal.gym.dto.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.domain.BoardCtg;
import renewal.gym.validator.groups.ValidationGroups;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class BoardWriteForm {

    @NotBlank(groups = ValidationGroups.NotEmptyGroup.class, message = "제목은 필수입니다.")
    private String title;

    private String content;
    private BoardCtg ctg;

    private Integer price;
    private LocalDateTime deadline;

}
