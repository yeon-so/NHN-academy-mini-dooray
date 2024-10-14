package com.nhnacademy.miniDooray.config.mapper;

import com.nhnacademy.miniDooray.dto.comment.CommentResponseDto;
import com.nhnacademy.miniDooray.dto.task.TaskDetailResponseDto;
import com.nhnacademy.miniDooray.dto.task.TaskResponseDto;
import com.nhnacademy.miniDooray.entity.Task;

import java.util.List;

public class TaskMapper {

    public static TaskResponseDto toResponseDto(long projectId, Task task, String tagStrings){
        return new TaskResponseDto(
                projectId,
                task.getId(),
                task.getProject().getProjectName(),
                task.getTaskName(),
                tagStrings,
                task.getMilestone().getMilestoneName(),
                task.getUser().getUserName()
        );
    }

    public static TaskDetailResponseDto toDetailResponseDto(long projectId,
                                                            Task task,
                                                            String tagStrings,
                                                            List<CommentResponseDto> commentList) {
        return new TaskDetailResponseDto(
                projectId,
                task.getId(),
                task.getProject().getProjectName(),
                task.getTaskName(),
                task.getTaskContent(),
                tagStrings,
                task.getMilestone().getMilestoneName(),
                task.getUser().getUserName(),
                commentList
        );
    }

}
