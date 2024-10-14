package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.Project;
import com.nhnacademy.miniDooray.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    boolean existsProjectMemberByProjectIdAndMemberId(long projectId, long memberId);
    List<ProjectMember> findByMemberId(Long userId);


}
