package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.PayStatus;
import renewal.gym.domain.Payment;
import renewal.gym.dto.pay.PayReceiptForm;
import renewal.gym.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Long save(Payment payment){
        return paymentRepository.save(payment).getId();
    }

    public List<PayReceiptForm> getReceipts(Long memberId) {
        return paymentRepository.findAllByMemberId(memberId)
                .stream()
                .map(Payment::toDto)
                .sorted(Comparator.comparing(PayReceiptForm::getRequestedAt).reversed())
                .toList();

    }

    public void updatePayment(Long id, PayStatus status) {
        paymentRepository.findById(id).ifPresent(payment -> payment.updateStatus(status));
    }
}
