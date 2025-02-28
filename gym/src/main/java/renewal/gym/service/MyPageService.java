package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.mypage.myChildForm;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final ChildRepository childRepository;

    public MyPageForm getMyPageForm(Long id) {
        return memberRepository.getMyPageForm(id);
    }

    public List<myChildForm> getMyChildForm(Long id) {
        return childRepository.findByMemberId(id);
    }
}
