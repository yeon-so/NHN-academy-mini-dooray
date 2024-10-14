package com.nhnacademy.miniDooray.dto.message;

import lombok.Value;

@Value
public class MessageResponseDto {
    int statusCode;
    String message;
}
