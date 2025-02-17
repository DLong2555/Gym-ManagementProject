package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import renewal.gym.domain.Role;

import java.util.List;

@Getter @Setter
public class LoginDTO {

    private Long id;
    private String loginId;
    private String password;
    private Role role;

    private List<Long> gymIds;

    @QueryProjection
    public LoginDTO(Long id, String loginId, String password, Role role, List<Long> gymIds) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
        this.gymIds = gymIds;
    }
}
