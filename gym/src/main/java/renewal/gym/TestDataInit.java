package renewal.gym;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import renewal.gym.domain.*;
import renewal.gym.dto.JoinManagerForm;
import renewal.gym.repository.*;
import renewal.gym.service.JoinService;
import renewal.gym.testdata.TestData;

import java.time.LocalDate;
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
    private final EventChildRepository eventChildRepository;
    private final ChildRepository childRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        try {
            Member member = new Member("testuser", "qlalfqjsgh12@#", "홍길동",
                    "01012345678",
                    new Address("12345", "천안시 청당동 21-12길", "빌리지 111호"));

            JoinManagerForm joinManagerForm = new JoinManagerForm("testmanager", "qlalfqjsgh12@#", "박라롱", "01025158821",
                    "3122", "충청남도 천안시 동남구 병천면 병천리 114-1", "1층 아우내 참 인성교육관",
                    "아우내 참 인성교육관", 130000, "01028479543");

            Child child = new Child("홍지훈", "01012345678", 5, "MALE");
            Child child2 = new Child("홍서연", "01012345678", 5, "MALE");
            Child child3 = new Child("홍라롱", "01012345678", 5, "MALE");
            Child child4 = new Child("홍정훈", "01012345678", 5, "MALE");


            Gym managerAndGym = createManagerAndGym(joinManagerForm);
            joinService.joinManger(managerAndGym);

            Period period = new Period(LocalDate.now(), LocalDate.now().plusMonths(1L));
            child.addMember(member);
            child.addGym(managerAndGym);
            child.registration(period);

            child2.addMember(member);
            child2.addGym(managerAndGym);
            child2.registration(period);

            child3.addMember(member);
            child3.addGym(managerAndGym);
            child3.registration(period);

            child4.addMember(member);
            child4.addGym(managerAndGym);
            child4.registration(period);

            TestData testData = new TestData();
            List<Gym> gyms = testData.gymListTestData();
            gymRepository.saveAll(gyms);

            joinService.join(member);

            childRepository.save(child);
            childRepository.save(child2);
            childRepository.save(child3);
            childRepository.save(child4);



        } catch (Exception e) {
            System.out.println("에러발생" + e.getMessage());
        }
    }

    @PostConstruct
    public void eventDataInit(){
        Manager manager = managerRepository.findById(1L).orElse(null);
        Gym gym = gymRepository.findById(1L).get();
        Gym gym2 = gymRepository.findById(2L).get();

        Long boardId = 0L;
        LocalDateTime now = LocalDateTime.now();
        Event event1 = boardRepository.save(new Event(manager, gym, "합숙", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                20000, now.minusDays(1)));

        Event event2 = boardRepository.save(new Event(manager, gym, "수영장", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                40000, now.plusDays(2)));

        Event event3 = boardRepository.save(new Event(manager, gym, "합숙", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                20000, now.plusDays(3)));

        Event event4 = boardRepository.save(new Event(manager, gym, "심사", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                40000, now.plusDays(4)));

        Event event5 = boardRepository.save(new Event(manager, gym, "농구", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                20000, now.plusDays(5)));

        Event event6 = boardRepository.save(new Event(manager, gym, "축구", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                40000, now.plusDays(7)));

        Event event7 = boardRepository.save(new Event(manager, gym, "합숙", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                20000, now.plusDays(7)));

        Event event8 = boardRepository.save(new Event(manager, gym2, "수영장", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                40000, now.plusDays(2)));

        Event event9 = boardRepository.save(new Event(manager, gym2, "합숙", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                20000, now.plusDays(3)));

        Event event10 = boardRepository.save(new Event(manager, gym2, "심사", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                40000, now.plusDays(4)));

        Event event11 = boardRepository.save(new Event(manager, gym2, "농구", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                20000, now.plusDays(5)));

        Event event12 = boardRepository.save(new Event(manager, gym2, "축구", "<img src=\"/gym/board/images/f8764a84-9b73-494b-954b-ce25a7d5e4b3.jpg\" width=\"280\" height=\"280\" class=\"uploadImage\" style=\"width: auto; height: auto;\">",
                40000, now.plusDays(7)));

        for (Child child : childRepository.findChildByIds(List.of(1L, 2L, 3L, 4L))) {
            eventChildRepository.save(new EventChild(event7, child));
        }

    }

    public Gym createManagerAndGym(JoinManagerForm joinForm) {

        Address address = new Address(joinForm.getZipcode(), joinForm.getRoadName(), joinForm.getDetailAddress());
        Manager manager = new Manager(joinForm.getMemId(), joinForm.getPassword(), joinForm.getName(), joinForm.getPhone());

        return new Gym(joinForm.getGymName(), joinForm.getGymPrice(), joinForm.getGymPhone(), address, manager);
    }


}