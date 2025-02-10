package renewal.gym.service.child;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Child;
import renewal.gym.domain.Gym;
import renewal.gym.domain.Member;
import renewal.gym.domain.Period;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.MemberRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChildRegisterService {

    private final ChildRepository childRepository;
    private final MemberRepository memberRepository;
    private final GymRepository gymRepository;

    @Transactional
    public Long register(Long id, Child child, String gymName, String address) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Gym gym = gymRepository.findByGymName(gymName, address).orElseThrow(IllegalArgumentException::new);

        log.debug("gym {}", gym.getId());
        log.debug("member {}", member.getId());


        Period period = new Period(LocalDateTime.now(), LocalDateTime.now().plusMonths(1L));
        child.registration(period);

        child.addMember(member);
        child.addGym(gym);

        childRepository.save(child);

        return gym.getId();
    }

}
