package com.nhnacademy.miniDooray.controller;

import com.nhnacademy.miniDooray.dto.message.MessageResponseDto;
import com.nhnacademy.miniDooray.dto.milestrone.MilestoneRequestDto;
import com.nhnacademy.miniDooray.service.CommonService;
import com.nhnacademy.miniDooray.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;
    private final CommonService commonService;

    @PostMapping("/{projectId}/milestone")
    public MessageResponseDto registerMilestone(
                                            @PathVariable String projectId,
                                            @RequestBody MilestoneRequestDto requestDto,
                                            @RequestHeader(value = "X-USER-ID", required = true) String userId) {

        long projectIdLong = parseId(projectId);
        commonService.isUserMemberOfProject(projectIdLong, userId);
        milestoneService.resgisterMilestone(projectIdLong, requestDto);

        return new MessageResponseDto(200, "OK");
    }

    @PutMapping("/{projectId}/milestone/{milestoneId}")
    public MessageResponseDto getTaskByProjectIdAndTaskId(
            @RequestBody MilestoneRequestDto requestDto,
            @PathVariable String projectId,
            @PathVariable String milestoneId,
            @RequestHeader(value = "X-USER-ID", required = true) String userId){

        long projectIdLong = parseId(projectId);
        long milestoneIdLong = parseId(milestoneId);
        commonService.isUserMemberOfProject(projectIdLong, userId);
        milestoneService.modifyMilestone(projectIdLong, milestoneIdLong, requestDto);

        return new MessageResponseDto(200, "OK");

    }

    @DeleteMapping("/{projectId}/milestone/{milestoneId}")
    public MessageResponseDto deleteTask(
            @PathVariable String projectId,
            @PathVariable String milestoneId,
            @RequestHeader(value = "X-USER-ID", required = true) String userId) {

        long projectIdLong = parseId(projectId);
        long milestoneIdLong = parseId(milestoneId);

        commonService.isUserMemberOfProject(projectIdLong, userId);
        milestoneService.deleteMilestone(projectIdLong, milestoneIdLong);

        return new MessageResponseDto(200, "OK");
    }

        private long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid format");
        }
    }
}
