package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryDslRepositoty {

    private final JPAQueryFactory queryFactory;

    public void deleteCommentsByTaskId(Long taskId) {
        queryFactory.delete(QComment.comment)
                .where(QComment.comment.task.id.eq(taskId))
                .execute();
    }

}
