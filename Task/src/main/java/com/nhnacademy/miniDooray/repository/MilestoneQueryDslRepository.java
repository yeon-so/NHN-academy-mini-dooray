package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.dto.milestrone.MilestoneRequestDto;
import com.nhnacademy.miniDooray.entity.Milestone;
import com.nhnacademy.miniDooray.entity.QMilestone;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MilestoneQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Transactional
    public void updateMilestone(long projectId, long milestoneId ,MilestoneRequestDto milestone) {

        queryFactory
                .update(QMilestone.milestone)
                .set(QMilestone.milestone.milestoneName, milestone.milestoneName())
                .where(QMilestone.milestone.id.eq(milestoneId)
                        .and(QMilestone.milestone.project.id.eq(projectId)))
                .execute();
    }

}
