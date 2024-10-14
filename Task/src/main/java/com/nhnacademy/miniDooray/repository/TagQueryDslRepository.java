package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.QComment;
import com.nhnacademy.miniDooray.entity.QTag;
import com.nhnacademy.miniDooray.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Tag findTagByProjectIdAndTagName(long projectId, String tagName) {
        return queryFactory.selectFrom(QTag.tag)
                .where(QTag.tag.project.id.eq(projectId)
                        .and(QTag.tag.tagName.eq(tagName)))
                .fetchOne();
    }

}
