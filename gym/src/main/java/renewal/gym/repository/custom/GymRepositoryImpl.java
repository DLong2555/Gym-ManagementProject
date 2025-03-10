package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import renewal.gym.domain.QGym;
import renewal.gym.dto.GymListDto;
import renewal.gym.dto.QGymListDto;
import renewal.gym.dto.mypage.MyGymForm;
import renewal.gym.dto.mypage.QMyGymForm;
import renewal.gym.repository.GymRepository;

import java.util.List;
import java.util.Set;

import static renewal.gym.domain.QGym.gym;

public class GymRepositoryImpl implements GymRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GymRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GymListDto> findGymNames(Set<Long> gymIds) {

        return queryFactory.select(new QGymListDto(
                        gym.id,
                        gym.gymName
                ))
                .from(gym)
                .where(gym.id.in(gymIds))
                .fetch();

    }

    @Override
    public List<MyGymForm> findMyGymList(Long id) {
        return queryFactory.select(new QMyGymForm(
                        gym.id,
                        gym.gymName,
                        gym.gymPrice.as("price"),
                        gym.gymPhoneNum,
                        gym.address.zipCode,
                        gym.address.roadName,
                        gym.address.detailAddress
                )).from(gym)
                .where(gym.manager.id.eq(id))
                .fetch();
    }
}
