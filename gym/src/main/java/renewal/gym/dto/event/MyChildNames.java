package renewal.gym.dto.event;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class MyChildNames {

    private Long id;
    private String name;

    @QueryProjection
    public MyChildNames(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
