package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import renewal.gym.domain.Role;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter @Setter
@ToString
public class LoginUserSession {

    private Long id;
    private String loginId;
    private Role role;

    private List<Long> gymIds;

    public LoginUserSession(Long id, String loginId, Role role, List<Long> gymIds) {
        this.id = id;
        this.loginId = loginId;
        this.role = role;
        this.gymIds = gymIds;
    }
}
