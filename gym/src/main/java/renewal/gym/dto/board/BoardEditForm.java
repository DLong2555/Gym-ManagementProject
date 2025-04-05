package renewal.gym.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.domain.BoardCtg;
import renewal.gym.validator.groups.ValidationGroups;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter @Setter
public class BoardEditForm {

    @NotBlank(groups = ValidationGroups.NotEmptyGroup.class, message = "제목은 필수입니다.")
    private String title;

    private String content;
    private BoardCtg ctg;

    private Integer price;
    private LocalDateTime deadline;

    @QueryProjection
    public BoardEditForm(String title, String content, String ctg, Integer price, LocalDateTime deadline) {
        this.title = title;
        this.content = content;
        this.ctg = BoardCtg.valueOf(ctg);
        this.price = price;
        this.deadline = deadline;
    }
}
