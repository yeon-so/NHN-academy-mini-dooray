package com.nhnacademy.miniDooray.service.impl;

import com.nhnacademy.miniDooray.dto.comment.CommentCreateRequestDto;
import com.nhnacademy.miniDooray.entity.Comment;
import com.nhnacademy.miniDooray.entity.Project;
import com.nhnacademy.miniDooray.entity.Task;
import com.nhnacademy.miniDooray.entity.User;
import com.nhnacademy.miniDooray.repository.CommentRepository;
import com.nhnacademy.miniDooray.repository.ProjectRepository;
import com.nhnacademy.miniDooray.repository.TaskRepository;
import com.nhnacademy.miniDooray.repository.UserRepository;
import com.nhnacademy.miniDooray.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public Comment createComment(Task task,Long projectId, CommentCreateRequestDto commentCreateRequestDto){

        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid project ID");
        }

        User user = userRepository.findUserByUserName(commentCreateRequestDto.getUserName());


        Comment comment = Comment.builder()
                .task(task)
                .user(user) //유저객체 받아올 메서드 이용해서 User 받아옴
                .content(commentCreateRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long projectId,Long taskId,Long commentId, String newContent, long userId) {

        Project project = projectRepository.findProjectById(projectId);
        Task task = taskRepository.findByProjectIdAndId(projectId, taskId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid project ID");
        } else if(task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid task ID");
        }

        Comment comment = commentRepository.findCommentByIdAndUserId(commentId, userId);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저의 댓글이 아닙니다.");
        }
        comment.setContent(newContent);
        log.info("@@@@@@@@@@@@@@@@@@@@@@ comment 수정: {}, {}", comment.getId(), comment.getContent());

        return commentRepository.save(comment);
    }


    @Override
    public void deleteComment(Long projectId, Long taskId,Long commentId, long userId) {
        Project project = projectRepository.findProjectById(projectId);
        Task task = taskRepository.findByProjectIdAndId(projectId, taskId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid project ID");
        } else if(task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid task ID");
        }

        Comment comment = commentRepository.findCommentByIdAndUserId(commentId, userId);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저의 댓글이 아닙니다.");
        }

        commentRepository.deleteById(commentId);
    }

//    public List<Comment> getCommentsByTask(Task task) {
//        return commentRepository.findByTask(task);
//    }

}
