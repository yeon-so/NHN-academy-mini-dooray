package com.nhnacademy.miniDooray.dto.comment;

public record CommentResponseDto(
        long commentId,
        String commentContent,
        String userName)
{ }
