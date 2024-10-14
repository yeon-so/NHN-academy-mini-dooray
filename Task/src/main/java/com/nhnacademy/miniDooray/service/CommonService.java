package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.exception.UserNotInProjectException;
import com.nhnacademy.miniDooray.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final ProjectMemberRepository projectMemberRepository;


    //응답헤더로 넘어온 user가 프로젝트 멤버인지 체크
    public void isUserMemberOfProject(long projectId, String userId) {
        if (!projectMemberRepository.existsProjectMemberByProjectIdAndMemberId(projectId, Integer.parseInt(userId))) {
            throw new UserNotInProjectException();
        }
    }
}
