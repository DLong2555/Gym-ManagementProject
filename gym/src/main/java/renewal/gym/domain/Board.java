package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @OneToMany(mappedBy = "board")
    private List<Comments> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BoardCtg boardCtg;
}
