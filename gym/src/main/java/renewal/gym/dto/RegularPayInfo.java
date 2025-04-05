package renewal.gym.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter @Setter
public class RegularPayInfo {

    private String title;
    private List<Long> childIds;
    private String orderId;
}
