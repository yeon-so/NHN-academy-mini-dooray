package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.entity.Project;
import com.nhnacademy.miniDooray.entity.ProjectMember;
import com.nhnacademy.miniDooray.entity.User;
import com.nhnacademy.miniDooray.exception.MemberAlreadyExistsInProjectException;
import com.nhnacademy.miniDooray.repository.ProjectMemberRepository;
import com.nhnacademy.miniDooray.repository.ProjectRepository;
import com.nhnacademy.miniDooray.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public void inviteMember(long projectId, List<Long> userIds) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project = projectOptional.get();

        for (long userId : userIds) {
            if (projectMemberRepository.existsProjectMemberByProjectIdAndMemberId(projectId, userId)) {
                throw new MemberAlreadyExistsInProjectException();
            }

            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                continue;
            }
            User user = userOptional.get();

            ProjectMember projectMember = new ProjectMember();
            projectMember.setProject(project);
            projectMember.setMember(user);
            projectMember.setMemberRole(ProjectMember.Role.MEMBER);
            projectMemberRepository.save(projectMember);
        }
    }
}
