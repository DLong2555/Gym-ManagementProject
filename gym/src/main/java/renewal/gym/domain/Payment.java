package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;
import renewal.gym.dto.pay.PayReceiptForm;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment extends Auditable{

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    private String orderId;
    private String orderName;
    private Long amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String paymentKey;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Enumerated(EnumType.STRING)
    private PayStatus status;

    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;

    private String description;

    public Payment(String orderId, String orderName, Long amount, Member member, String paymentKey, PayType payType, PayStatus status, LocalDateTime requestedAt, LocalDateTime approvedAt, String description) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.member = member;
        this.paymentKey = paymentKey;
        this.payType = payType;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.description = description;
    }

    public void updateStatus(PayStatus status) {
        this.status = status;
    }

    public PayReceiptForm toDto() {
        return PayReceiptForm.builder()
                .id(id)
                .orderName(orderName)
                .description(description) // 예시, 실제 필드 확인 필요
                .amount(amount)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .paymentKey(paymentKey)
                .payType(payType)
                .payStatus(status)
                .build();
    }

}
