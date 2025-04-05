package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import renewal.gym.dto.board.BoardWriteForm;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "board_type")
@DiscriminatorValue("ANNOUNCEMENT")
public class Board extends Auditable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private String title;

    @Lob
    private String content;

    private Long views;

    @Enumerated(EnumType.STRING)
    @Column(name="board_type", insertable = false, updatable = false)
    private BoardCtg ctg;

    public Board(Manager manager, Gym gym, String title, String content) {
        this.manager = manager;
        this.gym = gym;
        this.title = title;
        this.content = content;
    }

    @PrePersist
    protected void setViewsDefault() {
        if (views == null) {
            views = 0L;
        }
    }

    public void increaseViewCount() {
        views++;
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
