package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import renewal.gym.domain.QBoard;
import renewal.gym.domain.QEvent;
import renewal.gym.domain.QGym;
import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.dto.event.QEventInfoForm;

import static renewal.gym.domain.QBoard.board;
import static renewal.gym.domain.QEvent.event;
import static renewal.gym.domain.QGym.gym;

@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public EventInfoForm findEventInfoById(Long id) {
        return queryFactory.select(new QEventInfoForm(
                        board.title,
                        event.price,
                        board.gym.id
                ))
                .from(board)
                .leftJoin(gym).on(gym.id.eq(board.gym.id))
                .leftJoin(event).on(event.id.eq(board.id))
                .where(board.id.eq(id))
                .fetchOne();
    }
}
