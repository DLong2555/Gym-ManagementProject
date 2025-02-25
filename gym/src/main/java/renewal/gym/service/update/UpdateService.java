package renewal.gym.service.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Child;
import renewal.gym.domain.Period;
import renewal.gym.dto.manage.EditChildForm;
import renewal.gym.repository.ChildRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpdateService {

    private final ChildRepository childRepository;

    @Transactional
    public boolean updateChild(EditChildForm editChildForm) {
        Child findChild = childRepository.findById(editChildForm.getId()).orElse(null);

        if (findChild == null) return true;

        findChild.updateChild(editChildForm.getBelt(), new Period(editChildForm.getStartDate(), editChildForm.getEndDate()));

        return false;
    }
}
