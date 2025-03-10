package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Board extends Auditable{

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private String title;

    @Lob
    private String content;

    private Long views;

    @OneToMany(mappedBy = "board")
    private List<Comments> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BoardCtg boardCtg;
}
