package renewal.gym.dto.pay;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import renewal.gym.domain.PayStatus;
import renewal.gym.domain.PayType;
import renewal.gym.domain.Payment;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
public class PayReceiptForm {

    private Long id;
    private String orderName;
    private String description;
    private Long amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedAt;

    private String paymentKey;
    private PayType payType;
    private PayStatus payStatus;

}
