package renewal.gym.dto.pay;

import lombok.Getter;

@Getter
public class PayRequest {

    private String paymentKey;
    private String orderId;
    private Integer amount;
}
