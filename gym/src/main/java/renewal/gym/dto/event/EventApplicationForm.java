package renewal.gym.dto.event;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class EventApplicationForm {

    private Long id;
    private String title;
    private Integer price;

    private Long gymId;

    @QueryProjection
    public EventApplicationForm(Long id, String title, Integer price, Long gymId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.gymId = gymId;
    }
}
