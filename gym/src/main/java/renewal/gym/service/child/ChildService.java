package renewal.gym.service.child;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Child;
import renewal.gym.domain.Gym;
import renewal.gym.domain.Member;
import renewal.gym.domain.Period;
import renewal.gym.dto.RegularPaymentForm;
import renewal.gym.dto.register.ParentInfoForm;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final MemberRepository memberRepository;
    private final GymRepository gymRepository;

    public Long register(Long id, Long gymId, Child child) {

        Child findChild = childRepository.findByMemberIdAndChildName(id, child.getChildName()).orElse(null);
        Gym gym = gymRepository.findById(gymId).orElseThrow(IllegalArgumentException::new);

        // 체육관을 바꿀 때
        if(findChild != null){
            findChild.registerAnotherGym(child, gym);

            Period period = new Period(LocalDate.now(), LocalDate.now().plusMonths(1L));
            findChild.registration(period);
            return gym.getId();
        }

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        log.debug("gym {}", gym.getId());
        log.debug("member {}", member.getId());

        Period period = new Period(LocalDate.now(), LocalDate.now().plusMonths(1L));
        child.registration(period);

        child.addMember(member);
        child.addGym(gym);

        childRepository.save(child);

        return gym.getId();
    }

    public void childRegisterCancel(Long id, String name) {
        childRepository.deleteByMemberAndChildName(id, name);
    }

    public ParentInfoForm getParentInfo(Long id) {
        return memberRepository.getParentInfoForm(id);
    }

    public boolean duplicationCheck(Long gymId, Long memId, String childName) {
       return childRepository.findByGymIdAndMemberIdAndChildName(gymId, memId, childName).isPresent();
    }

    public List<RegularPaymentForm> getMyRegularPayment(Long id) {
        return childRepository.findMyRegularPayment(id);
    }

    public void updateEndDate(List<Long> childIds) {
        List<Child> findChilds = childRepository.findChildByIds(childIds);

        for (Child findChild : findChilds) {
            LocalDate endDate = findChild.getPeriod().getEndDate();

            if(endDate.isBefore(LocalDate.now())){
                findChild.getPeriod().changeStartDate();
                endDate = LocalDate.now().plusMonths(1L);
            }else{
                endDate = endDate.plusMonths(1L);
            }

            findChild.getPeriod().changeEndDate(endDate);
        }
    }
}
