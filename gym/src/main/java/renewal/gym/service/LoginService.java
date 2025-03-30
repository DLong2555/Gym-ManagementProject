package renewal.gym.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.*;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.repository.MemberRepository;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final GymRepository gymRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureIdEncryptor secureIdEncryptor;

    public LoginUserSession login(String memId, String password) {

        log.debug("memId: {}, password: {}", memId, password);

        Member findUser = memberRepository.findByMemId(memId)
                .filter(member -> passwordEncoder.matches(password, member.getPassword()))
                .orElse(null);

        Manager findManager = managerRepository.findByManageId(memId)
                .filter(manager -> passwordEncoder.matches(password, manager.getPassword()))
                .orElse(null);

        log.debug("findUser: {}", findUser);
        log.debug("findManager: {}", findManager);

        if (findUser != null) return createLoginUserSession(findUser);
        else if (findManager != null) return createLoginManagerSession(findManager);
        else return null;
    }

    public LoginUserSession createLoginUserSession(Member member) {
        return new LoginUserSession(member.getId(), member.getMemId(), member.getRole(), getMyGymList(member.getId()));
    }

    public Set<String> getMyGymList(Long id) {
        return memberRepository.getMyGymList(id)
                .stream()
                .map(secureIdEncryptor::encryptId)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LoginUserSession createLoginManagerSession(Manager manager) {
        Set<String> gymIds = gymRepository.findByManagerId(manager.getId())
                .stream().map(Gym::getId)
                .map(secureIdEncryptor::encryptId).collect(Collectors.toCollection(LinkedHashSet::new));

        return new LoginUserSession(manager.getId(), manager.getManageId(), manager.getRole(), gymIds);
    }
}
