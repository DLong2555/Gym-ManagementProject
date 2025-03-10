package renewal.gym.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import renewal.gym.domain.BoardCtg;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardInfoForm {

    private Long id;
    private String title;
    private String author;
    private Long views;
    private BoardCtg boardCtg;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @QueryProjection
    public BoardInfoForm(Long id, String title, String author, Long views, BoardCtg boardCtg, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.views = views;
        this.boardCtg = boardCtg;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
