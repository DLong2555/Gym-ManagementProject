package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.domain.Role;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
public class LoginDTO {

    private Long id;
    private String loginId;
    private String password;
    private Role role;

    private List<Long> gymIds = new ArrayList<>();

    public LoginDTO(Long id, String loginId, String password, Role role) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }
}
