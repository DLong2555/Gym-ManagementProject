package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import renewal.gym.domain.QMember;
import renewal.gym.dto.MyPageForm;
import renewal.gym.dto.QMyPageForm;

import java.util.List;

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
                        member.address
                ))
                .from(member)
                .where(member.id.eq(id))
                .fetchFirst();
    }
}
