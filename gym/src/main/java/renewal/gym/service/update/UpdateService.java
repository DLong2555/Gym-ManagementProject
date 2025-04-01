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
import renewal.gym.error.DataNotFoundException;
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
        Child findChild = childRepository.findById(editChildForm.getId()).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));

        findChild.updateChild(editChildForm.getBelt(), new Period(editChildForm.getStartDate(), editChildForm.getEndDate()));

        return false;
    }

    public boolean updateUser(String memberId, MyPageForm myPageForm) {

        Member findMember = memberRepository.findByMemId(memberId).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));

        findMember.updateMember(myPageForm.getName(), myPageForm.getPhoneNumber(), new Address(
                myPageForm.getZipCode(), myPageForm.getRoadName(), myPageForm.getDetailAddress()));

        return true;
    }

    public boolean updateManager(String memberId, MyPageManagerForm myPageForm) {

        Manager findManager = managerRepository.findByManageId(memberId).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));

        findManager.updateManager(myPageForm.getName(), myPageForm.getPhoneNumber());

        return true;
    }

    public boolean updateMyChild(MyChildEditForm form) {
        Child findChild = childRepository.findById(form.getId()).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));

        findChild.updateChildInfo(form.getName(), form.getAge(), form.getGender(), form.getPhoneNumber());

        return false;
    }

    public boolean updateMyGym(MyGymForm form) {
        Gym findGym = gymRepository.findById(form.getGymId()).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));

        findGym.updateGym(form.getGymName(), form.getPrice(), form.getGymPhoneNum(),
                new Address(form.getZipCode(), form.getRoadName(), form.getDetailAddress()));

        return false;
    }

    public void deleteGymFromChild(Long childId) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));

        child.removeGym();
    }
}
