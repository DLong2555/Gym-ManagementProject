package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByMemberId(Long memberId);
}
