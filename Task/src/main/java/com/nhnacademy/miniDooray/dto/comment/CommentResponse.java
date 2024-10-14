package com.nhnacademy.miniDooray.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CommentResponse {
    private int statusCode;
    private String message;
}
