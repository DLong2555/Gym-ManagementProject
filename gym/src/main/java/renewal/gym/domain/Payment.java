package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;

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

    public Payment(String orderId, String orderName, Long amount, Member member, String paymentKey, PayType payType, PayStatus status, LocalDateTime requestedAt, LocalDateTime approvedAt) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.member = member;
        this.paymentKey = paymentKey;
        this.payType = payType;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }
}
