package renewal.gym.dto.pay;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PayCancelDto {

    private Long id;
    private String paymentKey;
    private String orderName;
    private String childName;
}
