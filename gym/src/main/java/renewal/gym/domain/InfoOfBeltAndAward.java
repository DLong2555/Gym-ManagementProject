package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class InfoOfBeltAndAward {

    @Id @GeneratedValue
    @Column(name = "info_id")
    private Long id;

    private String belt_dan;
    private String belt_rank;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "infoOfBeltAndAward")
    private List<Award> awards = new ArrayList<>();
}
