package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Gym;
import renewal.gym.domain.Manager;
import renewal.gym.domain.Member;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.repository.MemberRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinService {

    private final ManagerRepository managerRepository;
    private final GymRepository gymRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member) {

        member.passwordEncoding(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long joinManger(Gym managerAndGym) {
        Manager manager = managerAndGym.getManager();

        manager.passwordEncoding(passwordEncoder.encode(manager.getPassword()));

        gymRepository.save(managerAndGym);

        return manager.getId();
    }

    @Transactional
    public Long addGym(Gym gym) {
        return gymRepository.save(gym).getId();
    }

    public boolean duplicateMemberId(String memId) {

        boolean manager = managerRepository.findByManageId(memId).isPresent();
        boolean user = memberRepository.findByMemId(memId).isPresent();

        return manager || user;
    }

}
