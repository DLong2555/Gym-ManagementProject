package renewal.gym.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import renewal.gym.domain.QEvent;
import renewal.gym.dto.board.*;

import java.util.List;

import static renewal.gym.domain.QBoard.board;
import static renewal.gym.domain.QEvent.event;
import static renewal.gym.domain.QGym.gym;
import static renewal.gym.domain.QManager.manager;


@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardInfoForm> findBoardByGymId(Long gymId, Pageable pageable) {

        List<BoardInfoForm> content = queryFactory.select(
                        new QBoardInfoForm(
                                board.id,
                                board.title,
                                manager.managerName,
                                board.views,
                                board.ctg.stringValue(),
                                board.createdAt,
                                board.updatedAt,
                                event.price,
                                event.deadline
                        )
                ).from(board)
                .leftJoin(board.manager, manager)
                .leftJoin(board.gym, gym)
                .leftJoin(event).on(event.id.eq(board.id))
                .where(board.gym.id.eq(gymId))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(board.count())
                .from(board)
                .where(board.manager.id.eq(gymId))
                .fetchOne();

        if (count == null) {
            count = 0L;
        }

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public BoardContentForm findContentFormById(Long boardId) {
        return queryFactory.select(new QBoardContentForm(
                        board.id,
                        board.title,
                        board.content,
                        board.createdAt,
                        manager.id.as("authorId"),
                        manager.managerName.as("author"),
                        board.views,
                        board.ctg.stringValue(),
                        event.price,
                        event.deadline
                ))
                .from(board)
                .leftJoin(gym.manager, manager)
                .leftJoin(event).on(event.id.eq(board.id))
                .where(board.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public BoardEditForm findEditFormById(Long id) {
        return queryFactory.select(new QBoardEditForm(
                        board.title,
                        board.content,
                        board.ctg.stringValue(),
                        event.price,
                        event.deadline
                ))
                .from(board)
                .leftJoin(event).on(event.id.eq(board.id))
                .where(board.id.eq(id))
                .fetchOne();
    }
}
