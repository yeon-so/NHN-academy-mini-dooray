package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.entity.Project;
import com.nhnacademy.miniDooray.entity.ProjectMember;
import com.nhnacademy.miniDooray.entity.User;
import com.nhnacademy.miniDooray.exception.ProjectNotFoundException;
import com.nhnacademy.miniDooray.repository.ProjectMemberRepository;
import com.nhnacademy.miniDooray.repository.ProjectRepository;
import com.nhnacademy.miniDooray.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    //등록, 수정, 멤버 초대, 존재확인, 리스트 조회
    public boolean isExit(long projectId) {
        return projectRepository.existsById(projectId);
    }

    public void create(String projectName, long userId) {
        //내가 존재한 프로젝트와 같은 이름 프로젝트 생성 불가?
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectStatus(Project.ProjectStatus.ACTIVE);
        projectRepository.save(project);

        Optional<User> user = userRepository.findById(userId);
        ProjectMember projectMember = new ProjectMember();
        projectMember.setMember(user.get());
        projectMember.setProject(project);
        projectMember.setMemberRole(ProjectMember.Role.ADMIN);
        projectMemberRepository.save(projectMember);
    }

    public void update(long projectId, Project.ProjectStatus projectStatus) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setProjectStatus(projectStatus);
            projectRepository.save(project);
        } else {
            throw new ProjectNotFoundException();
        }
    }

    public List<Project> getProjects(long userId) {
        List<ProjectMember> projectMembers = projectMemberRepository.findByMemberId(userId);
        List<Project> projects = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            projects.add(projectMember.getProject());
        }
        return projects;
    }
}
