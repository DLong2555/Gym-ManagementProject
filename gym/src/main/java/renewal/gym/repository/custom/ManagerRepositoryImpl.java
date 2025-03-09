package renewal.gym.repository.custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import renewal.gym.dto.manage.ChildInfoForm;
import renewal.gym.dto.LoginDTO;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.mypage.MyPageManagerForm;
import renewal.gym.dto.mypage.QMyPageForm;
import renewal.gym.dto.mypage.QMyPageManagerForm;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static renewal.gym.domain.QChild.child;
import static renewal.gym.domain.QGym.gym;
import static renewal.gym.domain.QManager.manager;
import static renewal.gym.domain.QMember.*;

@Slf4j
@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<String, List<ParentsInfoForm>> getChildInfo(Set<Long> gymIds) {

        List<Tuple> results = queryFactory.select(
                        gym.gymName,
                        Projections.constructor(
                                ParentsInfoForm.class,
                                member.id,
                                member.memName,
                                member.memPhoneNum,
                                member.address.roadName,
                                member.address.detailAddress
                        ),
                        Projections.constructor(
                                ChildInfoForm.class,
                                child.id,
                                child.childName.as("name"),
                                child.childAge.as("age"),
                                child.childGender.as("gender"),
                                child.period.startDate,
                                child.period.endDate
                        )
                )
                .from(child)
                .join(child.member, member)
                .join(child.gym, gym)
                .where(child.gym.id.in(gymIds))
                .fetch();

        Map<String, Map<String, ParentsInfoForm>> groupedByGymName = results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(0, String.class),
                        Collectors.toMap(
                                tuple -> tuple.get(1, ParentsInfoForm.class).getMemName(),
                                tuple -> {
                                    ParentsInfoForm parentsInfoForm = tuple.get(1, ParentsInfoForm.class);
                                    ChildInfoForm childInfoForm = tuple.get(2, ChildInfoForm.class);
                                    if (childInfoForm != null) {
                                        if (parentsInfoForm.getChildren() == null) {
                                            parentsInfoForm.setChildren(new ArrayList<>());
                                        }
                                        parentsInfoForm.getChildren().add(childInfoForm);
                                    }
                                    return parentsInfoForm;
                                },
                                (existing, replacement) -> {
                                    if (replacement.getChildren() != null) {
                                        existing.getChildren().addAll(replacement.getChildren());
                                    }
                                    return existing;
                                }
                        )
                ));


        return groupedByGymName.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ArrayList<>(entry.getValue().values())
                ));

    }

    @Override
    public Map<String, List<ParentsInfoForm>> getChildInfo2(List<Long> gymIds) {

        List<Tuple> results = queryFactory.select(
                        gym.gymName,
                        Projections.constructor(
                                ParentsInfoForm.class,
                                member.id,
                                member.memName,
                                member.memPhoneNum,
                                member.address.roadName,
                                member.address.detailAddress
                        ),
                        Projections.constructor(
                                ChildInfoForm.class,
                                child.id,
                                child.childName.as("name"),
                                child.childAge.as("age"),
                                child.childGender.as("gender"),
                                child.period.startDate,
                                child.period.endDate
                        )
                )
                .from(child)
                .join(child.member, member)
                .join(child.gym, gym)
                .where(child.gym.id.in(gymIds))
                .fetch();

        Map<String, Map<String, ParentsInfoForm>> groupedByGymAndName = results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(0, String.class),
                        Collectors.toMap(
                                tuple -> tuple.get(1, ParentsInfoForm.class).getMemName(),
                                tuple -> {
                                    ParentsInfoForm parentsInfoForm = tuple.get(1, ParentsInfoForm.class);
                                    ChildInfoForm childInfoForm = tuple.get(2, ChildInfoForm.class);
                                    if (childInfoForm != null) {
                                        if (parentsInfoForm.getChildren() == null) {
                                            parentsInfoForm.setChildren(new ArrayList<>());
                                        }
                                        parentsInfoForm.getChildren().add(childInfoForm);
                                    }
                                    return parentsInfoForm;
                                },
                                (existing, replacement) -> {
                                    // 동일한 memName을 가진 부모 객체가 있을 경우, 자식 리스트를 병합
                                    if (replacement.getChildren() != null) {
                                        existing.getChildren().addAll(replacement.getChildren());
                                    }
                                    return existing;
                                }
                        )
                ));


        Map<String, List<ParentsInfoForm>> finalResult = groupedByGymAndName.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ArrayList<>(entry.getValue().values())
                ));

        return finalResult;

    }

    @Override
    public MyPageManagerForm getMyPageForm(Long id) {
        return queryFactory.select(new QMyPageManagerForm(
                        manager.managerName.as("name"),
                        manager.managerPhone.as("phoneNumber")
                )).from(manager)
                .where(manager.id.eq(id))
                .fetchFirst();
    }

    @Override
    public LoginDTO getLoginInfo(String loginId) {

        List<Tuple> tuples = queryFactory.select(
                        Projections.constructor(
                                LoginDTO.class,
                                manager.id,
                                manager.manageId.as("loginId"),
                                manager.password,
                                manager.role
                        ),
                        gym.id
                )
                .from(gym)
                .join(gym.manager, manager)
                .where(gym.manager.manageId.eq(loginId))
                .fetch();

        log.debug("tuple {}", tuples);

        return tuples.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                tuple -> tuple.get(0, LoginDTO.class),
                                Collectors.mapping(
                                        tuple -> tuple.get(1, Long.class),
                                        Collectors.toList()
                                )
                        ),
                        map -> {
                            Map.Entry<LoginDTO, List<Long>> entry = map.entrySet().iterator().next();
                            LoginDTO loginDTO = entry.getKey();
                            loginDTO.getGymIds().addAll(
                                    map.values().stream()
                                            .flatMap(List::stream)
                                            .distinct()
                                            .sorted()
                                            .toList()
                            );
                            return loginDTO;
                        }
                ));
    }
}
