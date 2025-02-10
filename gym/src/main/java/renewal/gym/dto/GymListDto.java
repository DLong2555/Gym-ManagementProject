package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GymListDto {

    private Long id;
    private String gymName;

    @QueryProjection
    public GymListDto(Long id, String gymName) {
        this.id = id;
        this.gymName = gymName;
    }
}
