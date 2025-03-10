package renewal.gym.dto.pay;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {

    private String orderId;
    private String orderName;
    private Long amount;
    private String payType;
    private String paymentKey;
    private String customerName;
    private String successUrl;
    private String failUrl;

    private String failReason;
    private boolean isCancelled;
    private String cancelReason;
    private String createdAt;
}
