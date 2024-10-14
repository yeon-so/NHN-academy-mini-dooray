package com.nhnacademy.miniDooray.DTO;

import java.time.ZonedDateTime;

// 에러 응답 시 record
public record ErrorResponse(String title, int status, ZonedDateTime timestamp) {
    public ErrorResponse(String title, int status) {
        this(title, status, ZonedDateTime.now());
    }
}