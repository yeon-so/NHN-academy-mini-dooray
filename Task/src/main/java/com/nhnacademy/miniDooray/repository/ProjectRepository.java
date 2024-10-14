package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectName(String name);
    Project findProjectById(long projectId);
}
