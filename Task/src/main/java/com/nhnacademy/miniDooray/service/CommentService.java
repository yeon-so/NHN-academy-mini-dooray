package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.dto.comment.CommentCreateRequestDto;
import com.nhnacademy.miniDooray.entity.Comment;
import com.nhnacademy.miniDooray.entity.Task;

public interface CommentService {
    Comment createComment(Task task, Long projectId,CommentCreateRequestDto commentCreateRequestDto);
    void deleteComment(Long projectId, Long taskId,Long commentId, long userId);
    Comment updateComment(Long projectId,Long taskId,Long commentId, String newContent, long userId);

}
