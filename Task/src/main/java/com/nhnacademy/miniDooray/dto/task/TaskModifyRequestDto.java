package com.nhnacademy.miniDooray.dto.task;

public record TaskModifyRequestDto(
        String taskTags,
        String taskName,
        String milestone,
        String content
) {
}
