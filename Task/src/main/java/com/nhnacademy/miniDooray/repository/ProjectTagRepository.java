package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.ProjectTag;
import com.nhnacademy.miniDooray.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {

    List<ProjectTag> findTagByTaskId(long taskId);

    void deleteAllByTaskId(long taskId);

    void deleteAllByTagId(long tagId);
}
