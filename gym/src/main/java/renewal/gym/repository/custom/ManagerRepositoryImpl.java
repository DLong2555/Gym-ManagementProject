package renewal.gym.repository.custom;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import renewal.gym.domain.QGym;
import renewal.gym.domain.QManager;
import renewal.gym.dto.ChildInfoForm;
import renewal.gym.dto.LoginDTO;
import renewal.gym.dto.QLoginDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static renewal.gym.domain.QChild.child;
import static renewal.gym.domain.QGym.gym;
import static renewal.gym.domain.QManager.manager;
import static renewal.gym.domain.QMember.*;

@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<String, List<ChildInfoForm>> getChildInfo(Long gymId) {
         return queryFactory
                .from(child)
                .join(member).on(member.id.eq(child.member.id))
                .where(child.gym.id.eq(gymId))
                .transform(groupBy(member.memName)
                        .as(GroupBy.list(
                                Projections.constructor(
                                        ChildInfoForm.class,
                                        child.childName.as("name"),
                                        child.childAge.as("age"),
                                        child.childGender.as("gender"),
                                        child.period.startDate,
                                        child.period.endDate,
                                        member.id.as("memberId")
                                )
                        ))
                );
    }

    @Override
    public LoginDTO getLoginInfo(String loginId) {

//        queryFactory.select(new QLoginDTO(
//                manager.id,
//                manager.managerId.as("loginId"),
//                manager.password
//                manager.role,
//                select(gym.id)
//                        .from(gym)
//                        .where()
//        ))




        return null;
    }
}
