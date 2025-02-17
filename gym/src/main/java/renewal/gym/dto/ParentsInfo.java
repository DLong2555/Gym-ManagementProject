package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParentsInfo {

    private Long id;
    private String name;

    @QueryProjection
    public ParentsInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
