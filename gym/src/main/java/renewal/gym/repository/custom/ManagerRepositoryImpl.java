package renewal.gym.repository.custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import renewal.gym.dto.manage.ChildInfoForm;
import renewal.gym.dto.LoginDTO;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.dto.mypage.MyPageManagerForm;
import renewal.gym.dto.mypage.QMyPageManagerForm;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;
import static renewal.gym.domain.QChild.child;
import static renewal.gym.domain.QGym.gym;
import static renewal.gym.domain.QManager.manager;
import static renewal.gym.domain.QMember.*;

@Slf4j
@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final LocalDate now = LocalDate.now();

    @Override
    public List<ParentsInfoForm> getChildInfo(Long gymId, String ctg) {

        List<Tuple> results = queryFactory.select(
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
                                child.belt,
                                child.period.startDate,
                                child.period.endDate
                        )
                )
                .from(child)
                .join(child.member, member)
                .join(child.gym, gym)
                .where(
                        child.gym.id.eq(gymId),
                        ctgEq(ctg)
                )
                .fetch();


        Map<String, ParentsInfoForm> tupleMap = new LinkedHashMap<>();
        for (Tuple tuple : results) {
            ParentsInfoForm parents = tuple.get(0, ParentsInfoForm.class);
            ChildInfoForm child = tuple.get(1, ChildInfoForm.class);

            parents.getChildren().add(child);
            if(tupleMap.containsKey(parents.getMemName())){
                tupleMap.get(parents.getMemName()).getChildren().add(child);
            }else{
                tupleMap.put(parents.getMemName(), parents);
            }
        }

//        Map<String, ParentsInfoForm> tupleMap = results.stream()
//                .collect(Collectors.toMap(
//                        tuple -> tuple.get(0, ParentsInfoForm.class).getMemName(), // 키: memName
//                        tuple -> {
//                            ParentsInfoForm parent = tuple.get(0, ParentsInfoForm.class);
//                            ChildInfoForm child = tuple.get(1, ChildInfoForm.class);
//                            if (child != null) {
//                                parent.getChildren().add(child);
//                            }
//                            return parent;
//                        }, // 값: ParentsInfoForm 객체
//                        (existing, replacement) -> {
//                            // 동일한 memName을 가진 부모가 있을 경우, 자식 정보를 병합
//                            if (replacement.getChildren() != null) {
//                                existing.getChildren().addAll(replacement.getChildren());
//                            }
//                            return existing;
//                        }, // 병합 로직
//                        LinkedHashMap::new // 삽입 순서 보장
//                ));

        return new ArrayList<>(tupleMap.values());
    }

    private BooleanExpression ctgEq(String ctg) {
        return hasText(ctg) ? child.period.endDate.lt(now) : child.period.endDate.goe(now);
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

    //    @Override
//    public Map<String, List<ParentsInfoForm>> getChildInfo(Set<Long> gymIds) {
//        LocalDate now = LocalDate.now();
//
//        List<Tuple> results = queryFactory.select(
//                        gym.gymName,
//                        Projections.constructor(
//                                ParentsInfoForm.class,
//                                member.id,
//                                member.memName,
//                                member.memPhoneNum,
//                                member.address.roadName,
//                                member.address.detailAddress
//                        ),
//                        Projections.constructor(
//                                ChildInfoForm.class,
//                                child.id,
//                                child.childName.as("name"),
//                                child.childAge.as("age"),
//                                child.childGender.as("gender"),
//                                child.belt,
//                                child.period.startDate,
//                                child.period.endDate
//                        )
//                )
//                .from(child)
//                .join(child.member, member)
//                .join(child.gym, gym)
//                .where(child.gym.id.in(gymIds)
//                        .and(child.period.endDate.goe(now)))
//                .fetch();
//
//        Map<String, Map<String, ParentsInfoForm>> groupedByGymName = results.stream()
//                .collect(Collectors.groupingBy(
//                        tuple -> tuple.get(0, String.class),
//                        Collectors.toMap(
//                                tuple -> tuple.get(1, ParentsInfoForm.class).getMemName(),
//                                tuple -> {
//                                    ParentsInfoForm parentsInfoForm = tuple.get(1, ParentsInfoForm.class);
//                                    ChildInfoForm childInfoForm = tuple.get(2, ChildInfoForm.class);
//                                    if (childInfoForm != null) {
//                                        if (parentsInfoForm.getChildren() == null) {
//                                            parentsInfoForm.setChildren(new ArrayList<>());
//                                        }
//                                        parentsInfoForm.getChildren().add(childInfoForm);
//                                    }
//                                    return parentsInfoForm;
//                                },
//                                (existing, replacement) -> {
//                                    if (replacement.getChildren() != null) {
//                                        existing.getChildren().addAll(replacement.getChildren());
//                                    }
//                                    return existing;
//                                }
//                        )
//                ));
//
//
//        return groupedByGymName.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        entry -> new ArrayList<>(entry.getValue().values())
//                ));
//
//    }
}
