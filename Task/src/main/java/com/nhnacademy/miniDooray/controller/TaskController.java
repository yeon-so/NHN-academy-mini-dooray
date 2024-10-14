package com.nhnacademy.miniDooray.controller;

import com.nhnacademy.miniDooray.dto.message.MessageResponseDto;
import com.nhnacademy.miniDooray.dto.task.TaskDetailResponseDto;
import com.nhnacademy.miniDooray.dto.task.TaskModifyRequestDto;
import com.nhnacademy.miniDooray.dto.task.TaskRegisterRequestDto;
import com.nhnacademy.miniDooray.dto.task.TaskResponseDto;
import com.nhnacademy.miniDooray.service.CommonService;
import com.nhnacademy.miniDooray.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CommonService commonService;

    @GetMapping("/{projectId}")
    public List<TaskResponseDto> getAllTasksByProjectId(
            @PathVariable String projectId,
            @RequestHeader(value = "X-USER-ID", required = true) String userId) {

        long id = parseId(projectId);

        commonService.isUserMemberOfProject(id, userId);
        return taskService.getAllTasksByProjectId(id);

    }

    @GetMapping("/{projectId}/{taskId}")
    public TaskDetailResponseDto getTaskByProjectIdAndTaskId(
            @PathVariable String projectId,
            @PathVariable String taskId,
            @RequestHeader(value = "X-USER-ID", required = true) String userId){

        long projectIdLong = parseId(projectId);
        long taskIdLong = parseId(taskId);

        commonService.isUserMemberOfProject(projectIdLong, userId);
        return taskService.getTaskByProjectIdAndTaskId(projectIdLong, taskIdLong);

    }

    @PostMapping("/{projectId}")
    public MessageResponseDto registerTask(@PathVariable String projectId,
                                           @RequestBody TaskRegisterRequestDto requestDto,
                                           @RequestHeader(value = "X-USER-ID", required = true) String userId){
       long projectIdLong = parseId(projectId);
        commonService.isUserMemberOfProject(projectIdLong, userId);
        taskService.resgisterTask(projectIdLong, requestDto);

        return new MessageResponseDto(200, "OK");
    }


    @PutMapping("/{projectId}/{taskId}")
    public MessageResponseDto getTaskByProjectIdAndTaskId(
            @RequestBody TaskModifyRequestDto requestDto,
            @PathVariable String projectId,
            @PathVariable String taskId,
            @RequestHeader(value = "X-USER-ID", required = true) String userId){

        long projectIdLong = parseId(projectId);
        long taskIdLong = parseId(taskId);

        commonService.isUserMemberOfProject(projectIdLong, userId);
        taskService.modifyTask(projectIdLong, taskIdLong, requestDto);

        return new MessageResponseDto(200, "OK");

    }

    @DeleteMapping("/{projectId}/{taskId}")
    public MessageResponseDto deleteTask(
            @PathVariable String projectId,
            @PathVariable String taskId,
            @RequestHeader(value = "X-USER-ID", required = true) String userId){

        long projectIdLong = parseId(projectId);
        long taskIdLong = parseId(taskId);

        commonService.isUserMemberOfProject(projectIdLong, userId);
        taskService.deleteTask(projectIdLong, taskIdLong);

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
