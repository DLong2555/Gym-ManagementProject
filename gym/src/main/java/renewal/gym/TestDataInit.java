package renewal.gym;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import renewal.gym.domain.*;
import renewal.gym.dto.JoinManagerForm;
import renewal.gym.repository.BoardRepository;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.repository.MemberRepository;
import renewal.gym.service.JoinService;
import renewal.gym.testdata.TestData;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final GymRepository gymRepository;
    private final JoinService joinService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ManagerRepository managerRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        try {
            Member member = new Member("maxol2558", "zkzktl25@#", "홍길동",
                    "01012345678",
                    new Address("12345", "천안시 청당동 21-12길", "빌리지 111호"));

            JoinManagerForm joinManagerForm = new JoinManagerForm("gkxkzp2558", "zkzktl25@#", "박라롱", "01025158821",
                    "3122", "충청남도 천안시 동남구 병천면 병천리 114-1", "1층 아우내 참 인성교육관",
                    "아우내 참 인성교육관", 130000, "01028479543");

            Gym managerAndGym = createManagerAndGym(joinManagerForm);
            joinService.joinManger(managerAndGym);

            TestData testData = new TestData();
            List<Gym> gyms = testData.gymListTestData();
            gymRepository.saveAll(gyms);

            joinService.join(member);

        } catch (Exception e) {
            System.out.println("에러발생" + e.getMessage());
        }
    }

    @PostConstruct
    public void eventDataInit(){
        Manager manager = managerRepository.findById(1L).orElse(null);
        Gym gym = gymRepository.findById(1L).get();

        Long boardId = 0L;
        LocalDateTime now = LocalDateTime.now();
        Event event1 = boardRepository.save(new Event(manager, gym, "합숙", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                20000, now.minusDays(1)));

        Event event2 = boardRepository.save(new Event(manager, gym, "수영장", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                40000, now.plusDays(7)));

    }

    public Gym createManagerAndGym(JoinManagerForm joinForm) {

        Address address = new Address(joinForm.getZipcode(), joinForm.getRoadName(), joinForm.getDetailAddress());
        Manager manager = new Manager(joinForm.getMemId(), joinForm.getPassword(), joinForm.getName(), joinForm.getPhone());

        return new Gym(joinForm.getGymName(), joinForm.getGymPrice(), joinForm.getGymPhone(), address, manager);
    }


}