package renewal.gym.dto.pay;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import renewal.gym.domain.PayType;
import renewal.gym.domain.Payment;

import java.util.UUID;

@Builder
@Getter @Setter
public class PaymentDto {

    private PayType payType;
    private Long amount;
    private String orderName;

    private String successUrl;
    private String failureUrl;


}
