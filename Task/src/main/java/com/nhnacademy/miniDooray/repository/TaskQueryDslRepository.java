package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.dto.task.TaskModifyRequestDto;
import com.nhnacademy.miniDooray.entity.Milestone;
import com.nhnacademy.miniDooray.entity.QTask;
import com.nhnacademy.miniDooray.entity.Task;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Transactional
    public void updateTask(long projectId, long taskId, Milestone milestone, TaskModifyRequestDto requestDto) {

        queryFactory
                .update(QTask.task)
                .set(QTask.task.taskName, requestDto.taskName())
                .set(QTask.task.taskContent, requestDto.content())
                .set(QTask.task.milestone, milestone)
                .where(QTask.task.id.eq(taskId)
                        .and(QTask.task.project.id.eq(projectId)))
                .execute();

    }

    public void updateTask(Task task, long projectId) {

        queryFactory
                .update(QTask.task)
                .set(QTask.task.milestone,(Milestone) null)
                .where(QTask.task.id.eq(task.getId())
                        .and(QTask.task.project.id.eq(projectId)))
                .execute();

    }
}
