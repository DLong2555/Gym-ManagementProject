package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.mypage.MyGymForm;
import renewal.gym.dto.mypage.MyPageManagerForm;
import renewal.gym.dto.mypage.MyChildForm;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final ChildRepository childRepository;
    private final ManagerRepository managerRepository;
    private final GymRepository gymRepository;

    public MyPageForm getMyPageForm(Long id) {
        return memberRepository.getMyPageForm(id);
    }

    public MyPageManagerForm getMyManagerForm(Long id) {return managerRepository.getMyPageForm(id);}

    public List<MyChildForm> getMyChildForm(Long id) {
        return childRepository.findByMemberId(id);
    }

    public List<MyGymForm> getMyGym(Long managerId) {
        return gymRepository.findMyGymList(managerId);
    }
}
