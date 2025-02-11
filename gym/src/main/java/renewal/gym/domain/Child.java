package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    @GeneratedValue
    @Column(name = "child_id")
    private Long id;

    private String childName;
    private String childPhoneNum;
    private int childAge;

    @Enumerated(EnumType.STRING)
    private Gender childGender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Embedded
    private Period period;

    //test용
    public Child(String childName, Member member, Gym gym) {
        this.childName = childName;
        this.member = member;
        this.gym = gym;
    }

    public Child(String childName, String childPhoneNum, int childAge, String gender) {
        this.childName = childName;
        this.childPhoneNum = childPhoneNum;
        this.childAge = childAge;
        this.childGender = Gender.valueOf(gender);
    }

    public void addMember(Member member) {
        this.member = member;
        member.getChildren().add(this);
    }

    public void addGym(Gym gym) {
        this.gym = gym;
        gym.getChildren().add(this);
    }

    public void registration(Period period) {
        this.period = period;
    }

}
