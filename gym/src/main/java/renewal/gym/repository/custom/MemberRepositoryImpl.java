package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static renewal.gym.domain.QChild.child;

public class MemberRepositoryImpl implements MemberSessionRepositoryCustom {

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
}
