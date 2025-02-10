//package renewal.gym.service;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import renewal.gym.domain.*;
//import renewal.gym.dto.JoinManagerForm;
//import renewal.gym.repository.ChildRepository;
//import renewal.gym.repository.GymRepository;
//import renewal.gym.repository.MemberRepository;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Transactional
//@SpringBootTest
//class JoinServiceTest {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Autowired
//    private JoinService JoinService;
//
//    @Autowired
//    private ChildRepository childRepository;
//
//    @Autowired
//    private GymRepository gymRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private JoinService joinService;
//
//    @Test
//    @DisplayName("회원가입 테스트")
//    void joinTest() {
//        //given
//        Member member = new Member("maxol2558","qwerty1234!","홍길동",
//                "홍홍", "01012345678",
//                new Address("12345", "천안시 청당동 21-12길","빌리지 111호"));
//
//        Long userId = JoinService.join(member);
//
//        Member findMember = memberRepository.findById(userId).orElseThrow(NoSuchElementException::new);
//
//        //then
//        assertEquals("maxol2558", member.getMemId());
//        assertEquals("홍길동", member.getMemName());
//        assertEquals("홍홍", member.getMemNick());
//        assertEquals("01012345678", member.getMemPhoneNum());
//        assertEquals("12345", member.getAddress().getZipCode());
//        assertEquals("천안시 청당동 21-12길", member.getAddress().getRoadName());
//        assertEquals("빌리지 111호", member.getAddress().getDetailAddress());
//    }
//
//    @Test
//    @DisplayName("중복 확인 테스트")
//    void duplicateTest(){
//        Member member = new Member("maxol2558","qwerty1234!","홍길동",
//                "홍홍", "01012345678",
//                new Address("12345", "천안시 청당동 21-12길","빌리지 111호"));
//
//        JoinService.join(member);
//
//        assertTrue(JoinService.duplicateMemberId(member.getMemId()));
//        assertTrue(JoinService.duplicateMemberNick(member.getMemNick()));
//    }
//
//    @Test
//    @Rollback(false)
//    void joinManagerTest(){
//        JoinManagerForm joinManagerForm = new JoinManagerForm("gkxkzp2558", "zkzktl25@#", "김연수", "01038432212", "12345", "천안시 청당동 21-12길", "빌리지 111호",
//                "아우내 합기도", 130000, "01062017834");
//
//        Gym managerAndGym = createManagerAndGym(joinManagerForm);
//
//        joinService.joinManger(managerAndGym);
//    }
//
//    public Gym createManagerAndGym(JoinManagerForm joinForm){
//
//        Address address = new Address(joinForm.getZipcode(), joinForm.getRoadName(), joinForm.getDetailAddress());
//        Manager manager = new Manager(joinForm.getMemId(), joinForm.getPassword(), joinForm.getMemName(), joinForm.getMemPhoneNum());
//
//        return new Gym(joinForm.getGymName(), joinForm.getGymPrice(), joinForm.getGymPhone(), address, manager);
//    }
//
//    @Test
//    void findMyMemberGymTest(){
//        Member member = new Member("maxol2558","qwerty1234!","홍길동",
//                "홍홍", "01012345678",
//                new Address("12345", "천안시 청당동 21-12길","빌리지 111호"));
////        Member member = memberRepository.findByMemId("maxol2558").get();
//
//        JoinService.join(member);
//
//        Gym gym1 = new Gym("아우내 합기도");
//        Gym gym2 = new Gym("성정 합기도");
//        Gym gym3 = new Gym("청수 합기도");
//
//        gymRepository.save(gym1);
//        gymRepository.save(gym2);
//        gymRepository.save(gym3);
//
//        Child child1 = new Child("홍춘식", member, gym1);
//        child1.addMember(member);
//        child1.addGym(gym1);
//
//        Child child2 = new Child("홍배범", member, gym1);
//        child2.addMember(member);
//        child2.addGym(gym2);
//
//        Child child3 = new Child("홍수지", member, gym3);
//        child3.addMember(member);
//        child3.addGym(gym3);
//
//        childRepository.save(child1);
//        childRepository.save(child2);
//        childRepository.save(child3);
//
//
//        List<Long> list = member.getChildren().stream().map(Child::getId).toList();
//        List<Long> myGymLists = memberRepository.findMyMemberGymLists(list);
//
//        assertEquals(3, myGymLists.size());
//    }
//
//}