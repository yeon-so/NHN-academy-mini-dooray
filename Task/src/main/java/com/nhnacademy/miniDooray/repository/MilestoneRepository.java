package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.Milestone;
import com.nhnacademy.miniDooray.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    boolean existsByProjectAndMilestoneName(Project project, String milestoneName);
    Milestone findByProjectIdAndMilestoneName(Long projectId, String milestoneName);
    Milestone findByProjectIdAndId(long projectId, long milestoneId);
    void deleteByIdAndProjectId(long projectId, long milestoneId);
}
