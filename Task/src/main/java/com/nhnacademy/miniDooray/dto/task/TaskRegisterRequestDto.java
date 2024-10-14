package com.nhnacademy.miniDooray.dto.task;

public record TaskRegisterRequestDto(
        String taskTags,
        String taskName,
        String milestone,
        String content,
        String userName
) {
}
