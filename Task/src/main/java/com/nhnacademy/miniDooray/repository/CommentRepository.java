package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.Comment;
import com.nhnacademy.miniDooray.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTask(Task task);

    List<Comment> findByTaskId(long taskId);
    Comment findCommentByIdAndUserId(long commentId, long userId);
}
