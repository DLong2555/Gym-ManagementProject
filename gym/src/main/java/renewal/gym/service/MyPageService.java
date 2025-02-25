package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.dto.MyPageForm;
import renewal.gym.repository.MemberRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;

    public MyPageForm getMyPageForm(Long id) {
        return memberRepository.getMyPageForm(id);
    }
}
