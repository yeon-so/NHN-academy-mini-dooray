package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(long projectId);

    Task findByProjectIdAndId(long projectId, long id);

    void deleteByIdAndProjectId(long projectId, long id);

    List<Task> findByProjectIdAndMilestoneId(long projectId, long milestoneId);
}
