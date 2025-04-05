package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.converter.Decrypt;
import renewal.gym.converter.Encrypt;

@ToString
@Getter @Setter
public class GymInfoDto {

    @Encrypt
    private Long id;

    private String gymName;

    @QueryProjection
    public GymInfoDto(Long id, String gymName) {
        this.id = id;
        this.gymName = gymName;
    }
}
