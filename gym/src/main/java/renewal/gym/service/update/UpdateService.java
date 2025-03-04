package renewal.gym.service.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.*;
import renewal.gym.dto.mypage.MyChildEditForm;
import renewal.gym.dto.mypage.MyGymForm;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.manage.EditChildForm;
import renewal.gym.dto.mypage.MyPageManagerForm;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateService {

    private final ChildRepository childRepository;
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;
    private final GymRepository gymRepository;

    public boolean updateChild(EditChildForm editChildForm) {
        Child findChild = childRepository.findById(editChildForm.getId()).orElse(null);

        if (findChild == null) return true;

        findChild.updateChild(editChildForm.getBelt(), new Period(editChildForm.getStartDate(), editChildForm.getEndDate()));

        return false;
    }

    public boolean updateUser(String memberId, MyPageForm myPageForm) {

        Member findMember = memberRepository.findByMemId(memberId).orElse(null);

        if (findMember == null) return false;

        findMember.updateMember(myPageForm.getName(), myPageForm.getPhoneNumber(), new Address(
                myPageForm.getZipCode(), myPageForm.getRoadName(), myPageForm.getDetailAddress()));

        return true;
    }

    public boolean updateManager(String memberId, MyPageManagerForm myPageForm) {

        Manager findManager = managerRepository.findByManageId(memberId).orElse(null);

        if (findManager == null) return false;

        findManager.updateManager(myPageForm.getName(), myPageForm.getPhoneNumber());

        return true;
    }

    public boolean updateMyChild(MyChildEditForm form) {
        Child findChild = childRepository.findById(form.getId()).orElse(null);

        if (findChild == null) return true;

        findChild.updateChildInfo(form.getName(), form.getAge(), form.getGender(), form.getPhoneNumber());

        return false;
    }

    public boolean updateMyGym(MyGymForm form) {
        Gym findGym = gymRepository.findById(form.getGymId()).orElse(null);

        if (findGym == null) return true;

        findGym.updateGym(form.getGymName(), form.getPrice(), form.getGymPhoneNum(),
                new Address(form.getZipCode(), form.getRoadName(), form.getDetailAddress()));

        return false;
    }
}
