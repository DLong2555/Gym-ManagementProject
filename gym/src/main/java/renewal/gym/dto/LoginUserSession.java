package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.domain.Role;


import java.util.Set;

@Getter @Setter
@ToString
public class LoginUserSession {

    private Long id;
    private String loginId;
    private Role role;

    private Set<String> gymIds;

    public LoginUserSession(Long id, String loginId, Role role, Set<String> gymIds) {
        this.id = id;
        this.loginId = loginId;
        this.role = role;
        this.gymIds = gymIds;
    }
}
