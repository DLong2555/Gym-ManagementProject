package renewal.gym;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import renewal.gym.domain.*;
import renewal.gym.repository.GymRepository;
import renewal.gym.service.JoinService;
import renewal.gym.testdata.TestData;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final GymRepository gymRepository;
    private final JoinService joinService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        try {
            Member member = new Member("maxol2558", "zkzktl25@#", "홍길동",
                    "01012345678",
                    new Address("12345", "천안시 청당동 21-12길", "빌리지 111호"));

//            JoinManagerForm joinManagerForm = new JoinManagerForm("gkxkzp2558", "zkzktl25@#", "박라롱", "01025158821",
//                    "3122", "충청남도 천안시 동남구 병천면 병천리 114-1", "1층 아우내 참 인성교육관",
//                    "아우내 참 인성교육관", 130000, "01028479543");

//            Gym managerAndGym = createManagerAndGym(joinManagerForm);
//            joinService.joinManger(managerAndGym);

            TestData testData = new TestData();
            List<Gym> gyms = testData.gymListTestData();
            gymRepository.saveAll(gyms);

            joinService.join(member);

        }catch (Exception e){
            System.out.println("에러발생" + e.getMessage());
        }
    }


}