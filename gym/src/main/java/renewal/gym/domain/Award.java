package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Award {

    @Id @GeneratedValue
    @Column(name = "awd_id")
    private Long id;

    private String awardName;
    private LocalDateTime awardDate;
    private String awardRank;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "info_id")
    private InfoOfBeltAndAward infoOfBeltAndAward;

    public void addInfoOfBeltAndAward(InfoOfBeltAndAward infoOfBeltAndAward){
        this.infoOfBeltAndAward = infoOfBeltAndAward;
        infoOfBeltAndAward.getAwards().add(this);
    }
}
