package com.nhnacademy.miniDooray.dto.task;

import com.nhnacademy.miniDooray.entity.Tag;
import com.nhnacademy.miniDooray.entity.Task;

import java.util.List;

public record TaskResponseDto (
    long projectId,
    long taskId,
    String projectName,
    String taskName,
    String taskTags,
    String milestone,
    String userName

){}
