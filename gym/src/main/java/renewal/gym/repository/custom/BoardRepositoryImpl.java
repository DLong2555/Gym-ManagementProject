package renewal.gym.repository.custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import renewal.gym.dto.board.BoardInfoForm;
import renewal.gym.dto.board.QBoardInfoForm;
import java.util.List;

import static renewal.gym.domain.QBoard.board;


@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardInfoForm> findBoardByManagerId(Long managerId, Long gymId, Pageable pageable) {

        List<BoardInfoForm> content = queryFactory.select(
                        new QBoardInfoForm(
                                board.id,
                                board.title,
                                board.gym.manager.managerName,
                                board.views,
                                board.boardCtg,
                                board.createdAt,
                                board.updatedAt
                        )
                ).from(board)
                .where(board.gym.manager.id.eq(managerId).and(board.gym.id.eq(gymId)))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(board.count())
                .from(board)
                .where(board.gym.manager.id.eq(managerId).and(board.gym.id.eq(gymId)))
                .fetchOne();

        if (count == null) {
            count = 0L;
        }

        return new PageImpl<>(content, pageable, count);
    }
}
