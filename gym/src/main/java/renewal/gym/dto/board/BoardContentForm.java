package renewal.gym.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import renewal.gym.domain.BoardCtg;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class BoardContentForm {

    private Long id;
    private String title;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private Long authorId;
    private String author;
    private Long views;

    private BoardCtg ctg;

    @NumberFormat(pattern = "#,###원")
    private Integer price;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    @QueryProjection
    public BoardContentForm(Long id, String title, String content, LocalDateTime createdAt, Long authorId, String author, Long views, String ctg,
                            Integer price, LocalDateTime deadline) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.author = author;
        this.views = views;
        this.ctg = BoardCtg.valueOf(ctg);
        this.price = price;
        this.deadline = deadline;
    }
}
