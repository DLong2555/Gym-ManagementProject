package renewal.gym.service.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.*;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.manage.EditChildForm;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpdateService {

    private final ChildRepository childRepository;
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public boolean updateChild(EditChildForm editChildForm) {
        Child findChild = childRepository.findById(editChildForm.getId()).orElse(null);

        if (findChild == null) return true;

        findChild.updateChild(editChildForm.getBelt(), new Period(editChildForm.getStartDate(), editChildForm.getEndDate()));

        return false;
    }

    @Transactional
    public boolean updateUser(String memberId, Role role, MyPageForm myPageForm) {
        if (role == Role.USER){
            Member findMember = memberRepository.findByMemId(memberId).orElse(null);

            if (findMember == null) return false;


            findMember.updateMember(myPageForm.getName(), myPageForm.getPhoneNumber(), new Address(
                    myPageForm.getZipCode(), myPageForm.getRoadName(), myPageForm.getDetailAddress()));

        }else if (role == Role.MANAGER){
            Manager findManager = managerRepository.findByManageId(memberId).orElse(null);

            if (findManager == null) return false;

        }

        return true;
    }
}
