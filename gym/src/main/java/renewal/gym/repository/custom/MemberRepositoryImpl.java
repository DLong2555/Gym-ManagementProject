package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.mypage.QMyPageForm;
import renewal.gym.dto.register.ParentInfoForm;
import renewal.gym.dto.register.QParentInfoForm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static renewal.gym.domain.QChild.child;
import static renewal.gym.domain.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> findMyMemberGymLists(List<Long> childList) {

        return queryFactory.select(child.gym.id)
                .distinct()
                .from(child)
                .where(child.id.in(childList))
                .fetch();
    }

    @Override
    public MyPageForm getMyPageForm(Long id) {
        return queryFactory.select(new QMyPageForm(
                        member.memName.as("name"),
                        member.memPhoneNum.as("phoneNumber"),
                        member.address.zipCode,
                        member.address.roadName,
                        member.address.detailAddress
                ))
                .from(member)
                .where(member.id.eq(id))
                .fetchFirst();
    }

    @Override
    public ParentInfoForm getParentInfoForm(Long id) {
        return queryFactory.select(new QParentInfoForm(
                        member.memName.as("name"),
                        member.memPhoneNum.as("phoneNumber")
                )).from(member)
                .where(member.id.eq(id))
                .fetchFirst();
    }

    @Override
    public Set<Long> getMyGymList(Long id) {
        List<Long> result = queryFactory.select(child.gym.id).distinct()
                .from(child)
                .where(child.member.id.eq(id))
                .fetch();

        return new HashSet<>(result);
    }
}
