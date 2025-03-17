package renewal.gym.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
public class EventPayForm {

    private Long boardId;
    private String title;
    private List<Long> childIds = new ArrayList<>();
    private String orderId;
}
