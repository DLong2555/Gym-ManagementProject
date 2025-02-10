package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String memId;

    private String password;
    private String memName;

    private String memPhoneNum;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Child> children = new ArrayList<>();

    public Member(String memId, String password, String memName, String memPhoneNum, Address address) {
        this.memId = memId;
        this.password = password;
        this.memName = memName;
        this.memPhoneNum = memPhoneNum;
        this.address = address;
    }

    public void passwordEncoding(String password){
        this.password = password;
    }
}
