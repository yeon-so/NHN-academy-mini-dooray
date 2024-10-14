package com.nhnacademy.miniDooray.dto.message;

import java.util.List;

public record MessageResponseArrayDto(
        int statusCode,
        List<String> message) {}

