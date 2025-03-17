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
    private Integer childAge;
    private String belt;

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

    public Child(String childName, String childPhoneNum, int childAge, String gender) {
        this.childName = childName;
        this.childPhoneNum = childPhoneNum;
        this.childAge = childAge;
        this.childGender = Gender.valueOf(gender);
        this.belt = "흰띠";
    }

    public void addMember(Member member) {
        this.member = member;
        member.getChildren().add(this);
    }

    public void addGym(Gym gym) {
        this.gym = gym;
    }

    public void registration(Period period) {
        this.period = period;
    }

    public void updateChild(String belt, Period period){
        this.belt = belt;
        this.period = period;
    }

    public void updateChildInfo(String name, Integer age, String gender, String phoneNumber) {
        this.childName = name;
        this.childAge = age;
        this.childGender = Gender.valueOf(gender);
        this.childPhoneNum = phoneNumber;
    }

    public void registerAnotherGym(Child child, Gym gym) {
        this.gym = gym;
        this.childName = child.getChildName();
        this.childAge = child.getChildAge();
        this.childGender = child.getChildGender();
        this.childPhoneNum = child.getChildPhoneNum();
        this.belt = null;
    }

}
