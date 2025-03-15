package renewal.gym.dto.event;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

@ToString
@Getter @Setter
public class EventInfoForm {

    private Long id;
    private String title;

    @NumberFormat(pattern = "#,###")
    private Integer price;

    private Long gymId;

    @QueryProjection
    public EventInfoForm(String title, Integer price, Long gymId) {
        this.title = title;
        this.price = price;
        this.gymId = gymId;
    }
}
