package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.dto.task.TaskDetailResponseDto;
import com.nhnacademy.miniDooray.dto.task.TaskModifyRequestDto;
import com.nhnacademy.miniDooray.dto.task.TaskRegisterRequestDto;
import com.nhnacademy.miniDooray.dto.task.TaskResponseDto;
import com.nhnacademy.miniDooray.entity.Task;

import java.util.List;

public interface TaskService {

    public List<TaskResponseDto> getAllTasksByProjectId(long projectId);

    TaskDetailResponseDto getTaskByProjectIdAndTaskId(long projectidLong, long taskIdLong);

    void resgisterTask(long projectId, TaskRegisterRequestDto requestDto);

    void modifyTask(long projectId, long taskId, TaskModifyRequestDto requestDto);

    void deleteTask(long projectId, long taskId);

    Task getTaskById(long projectId, long taskId);
}
