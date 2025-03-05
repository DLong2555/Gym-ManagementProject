package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Manager {

    @Id
    @GeneratedValue
    @Column(name = "manager_id")
    private Long id;

    @Column(nullable = false, unique = false)
    private String manageId;

    private String password;
    private String managerName;
    private String managerPhone;

    @Enumerated(EnumType.STRING)
    private Role role = Role.MANAGER;

    public Manager(String manageId, String password, String managerName, String managerPhone) {
        this.manageId = manageId;
        this.password = password;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
    }

    public void passwordEncoding(String password){
        this.password = password;
    }

    public void updateManager(String name, String phone){
        this.managerName = name;
        this.managerPhone = phone;
    }
}
