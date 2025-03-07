package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Payment;
import renewal.gym.repository.PaymentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Long save(Payment payment){
        return paymentRepository.save(payment).getId();
    }
}
