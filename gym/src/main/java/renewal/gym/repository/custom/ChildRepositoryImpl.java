package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import renewal.gym.dto.mypage.MyChildForm;
import renewal.gym.dto.mypage.QMyChildForm;

import java.util.List;

import static renewal.gym.domain.QChild.child;
import static renewal.gym.domain.QGym.gym;

@RequiredArgsConstructor
public class ChildRepositoryImpl implements ChildRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyChildForm> findByMemberId(Long memberId) {
        return queryFactory.select(new QMyChildForm(
                        child.id,
                        child.childName.as("name"),
                        child.childPhoneNum.as("phoneNumber"),
                        child.childAge.as("age"),
                        child.childGender.as("gender"),
                        child.gym.gymName,
                        child.belt,
                        child.period.startDate,
                        child.period.endDate
                ))
                .from(child)
                .join(child.gym, gym)
                .where(child.member.id.eq(memberId))
                .fetch();
    }
}
