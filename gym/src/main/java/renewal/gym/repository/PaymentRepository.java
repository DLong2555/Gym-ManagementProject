package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
