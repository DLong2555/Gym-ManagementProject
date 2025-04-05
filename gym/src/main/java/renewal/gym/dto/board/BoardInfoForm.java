package renewal.gym.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.domain.BoardCtg;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class BoardInfoForm {

    private Long id;
    private String title;
    private String author;
    private Long views;
    private BoardCtg ctg;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer price;
    private LocalDateTime deadline;

    @QueryProjection
    public BoardInfoForm(Long id, String title, String author, Long views, String ctg, LocalDateTime createdAt, LocalDateTime updatedAt,
                         Integer price, LocalDateTime deadline) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.views = views;
        this.ctg = BoardCtg.valueOf(ctg);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
        this.deadline = deadline;
    }
}
