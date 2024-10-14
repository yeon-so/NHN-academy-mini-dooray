package com.nhnacademy.miniDooray.dto.task;

import com.nhnacademy.miniDooray.dto.comment.CommentResponseDto;

import java.util.List;

public record TaskDetailResponseDto(
        long projectId,
        long taskId,
        String projectName,
        String taskName,
        String content,
        String taskTags,
        String taskMilstone,
        String userName,
        List<CommentResponseDto> comments

) {
}
