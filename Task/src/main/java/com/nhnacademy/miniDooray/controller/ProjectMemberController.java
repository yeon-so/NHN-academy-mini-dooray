package com.nhnacademy.miniDooray.controller;

import com.nhnacademy.miniDooray.dto.project.InviteMemberRequest;
import com.nhnacademy.miniDooray.dto.message.MessageResponseDto;
import com.nhnacademy.miniDooray.service.CommonService;
import com.nhnacademy.miniDooray.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;
    private final CommonService commonService;

    //4. 프로젝트 멤버 초대
    @PostMapping("/{projectId}/invite")
    public ResponseEntity<MessageResponseDto> inviteMember(@PathVariable long projectId,
                                                           @RequestBody InviteMemberRequest inviteMemberRequest,
                                                           @RequestHeader(value = "X-USER-ID", required = true) String userId) {
        commonService.isUserMemberOfProject(projectId, userId);
        projectMemberService.inviteMember(projectId, inviteMemberRequest.getUserIds());
        MessageResponseDto statusResponse = new MessageResponseDto(200, "OK");
        return ResponseEntity.ok(statusResponse);
    }
}
