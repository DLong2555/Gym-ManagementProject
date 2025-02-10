package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import renewal.gym.domain.QGym;
import renewal.gym.dto.GymListDto;
import renewal.gym.dto.QGymListDto;
import renewal.gym.repository.GymRepository;

import java.util.List;

import static renewal.gym.domain.QGym.gym;

public class GymRepositoryImpl implements GymRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public GymRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GymListDto> findGymList() {

        return jpaQueryFactory.select(new QGymListDto(
                        gym.id,
                        gym.gymName
                )).from(gym)
                .fetch();

    }
}
