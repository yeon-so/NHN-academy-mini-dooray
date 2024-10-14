package com.nhnacademy.miniDooray.service.impl;

import com.nhnacademy.miniDooray.config.mapper.TaskMapper;
import com.nhnacademy.miniDooray.dto.comment.CommentResponseDto;
import com.nhnacademy.miniDooray.dto.task.TaskDetailResponseDto;
import com.nhnacademy.miniDooray.dto.task.TaskModifyRequestDto;
import com.nhnacademy.miniDooray.dto.task.TaskRegisterRequestDto;
import com.nhnacademy.miniDooray.dto.task.TaskResponseDto;
import com.nhnacademy.miniDooray.entity.*;
import com.nhnacademy.miniDooray.repository.*;
import com.nhnacademy.miniDooray.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectTagRepository projectTagRepository;
    private final TaskQueryDslRepository taskQueryDslRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentQueryDslRepositoty commentQueryDslRepositoty;
    private final TagRepository tagRepository;
    private final TagQueryDslRepository tagQueryDslRepository;

    private static final String TAG_DELIMITER = ", ";

    @Override
    public List<TaskResponseDto> getAllTasksByProjectId(long projectId) {

        List<Task> taskList = taskRepository.findByProjectId(projectId);

        return taskList.stream()
                .map(task -> convertToTaskResponseDto(projectId,task))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDetailResponseDto getTaskByProjectIdAndTaskId(long projectId, long taskId) {

        Task task = taskRepository.findByProjectIdAndId(projectId, taskId);

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid project ID or task ID");
        }

        return convertToTaskDetailResponseDto(projectId, task);
    }

    @Override
    @Transactional
    public void resgisterTask(long projectId, TaskRegisterRequestDto requestDto) {

        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid project ID");
        }

        User user = userRepository.findUserByUserName(requestDto.userName());
        Milestone milestone = milestoneRepository.findByProjectIdAndMilestoneName(projectId, requestDto.milestone());

        Task task = new Task(
                project,
                milestone,
                user,
                requestDto.taskName(),
                requestDto.content(),
                LocalDateTime.now());

        saveTaskWithTags(task, projectId, requestDto.taskTags());

    }

    @Override
    @Transactional
    public void modifyTask(long projectId, long taskId, TaskModifyRequestDto requestDto) {
        Project project = projectRepository.findProjectById(projectId);
        Task task = taskRepository.findByProjectIdAndId(projectId, taskId);
        Milestone milestone = milestoneRepository.findByProjectIdAndMilestoneName(projectId, requestDto.milestone());

        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid project ID");
        } else if(milestone == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid milestone");
        } else if(task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid task ID");
        } else if(requestDto.taskName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task name must be between 1 and 50 characters");
        }

        taskQueryDslRepository.updateTask(projectId, taskId, milestone, requestDto);
        modifyTags(projectId, taskId, requestDto.taskTags(),task);

    }

    @Override
    @Transactional
    public void deleteTask(long projectId, long taskId) {

        Project project = projectRepository.findProjectById(projectId);
        Task task = taskRepository.findByProjectIdAndId(projectId, taskId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid project ID");
        } else if(task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid task ID");
        }

        commentQueryDslRepositoty.deleteCommentsByTaskId(taskId);
        projectTagRepository.deleteAllByTaskId(taskId);
        taskRepository.deleteByIdAndProjectId(projectId,taskId);
    }

    private void modifyTags(long projectId, long taskId, String taskTags, Task task){
        List<ProjectTag> existTagList = projectTagRepository.findTagByTaskId(taskId);
        List<String> newTagList = Arrays.asList(taskTags.split(TAG_DELIMITER));

        List<ProjectTag> removeTagList = existTagList.stream()
                .filter(tag -> !newTagList.contains(tag.getTag().getTagName()))
                .toList();

        for (ProjectTag tag : removeTagList) {
            projectTagRepository.delete(tag);
        }

        for (String tagName : newTagList) {
            if (!tagName.isEmpty() &&
                    existTagList.stream().noneMatch(tag -> tag.getTag().getTagName().equals(tagName))) {

                Tag tag = tagRepository.findTagByProjectIdAndTagName(projectId, tagName);

                if(Objects.nonNull(tag)){
                    ProjectTag newProjectTag = new ProjectTag(
                            tag,
                            task
                    );
                    projectTagRepository.save(newProjectTag);
                }
            }
        }
    }

    private void saveTaskWithTags(Task task, long projectId, String taskTags) {
        try {
            taskRepository.save(task);
            String[] tags = taskTags.split(TAG_DELIMITER);

            for(String tagName : tags){
                if(!tagName.isEmpty()){
                    ProjectTag projectTag = new ProjectTag(
                            tagQueryDslRepository.findTagByProjectIdAndTagName(projectId, tagName),
                            task
                    );
                    projectTagRepository.save(projectTag);
                }
            }

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Task Save Failed");
        }
    }

    private TaskResponseDto convertToTaskResponseDto(long projectId, Task task) {
        String tagStrings = getTagNamesByTaskId(task.getId());
        return TaskMapper.toResponseDto(projectId,task,tagStrings);
    }

    private TaskDetailResponseDto convertToTaskDetailResponseDto(long projectId, Task task) {
        String tagStrings = getTagNamesByTaskId(task.getId());
        List<Comment> commentList = commentRepository.findByTaskId(task.getId());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        // TODO : Stub
        for(Comment comment : commentList){
            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUser().getUserName()
            );
            commentResponseDtoList.add(commentResponseDto);
        }

        return TaskMapper.toDetailResponseDto(projectId, task,tagStrings,commentResponseDtoList);
    }

    private String getTagNamesByTaskId(long taskId) {
        List<ProjectTag> tags = projectTagRepository.findTagByTaskId(taskId);
        return tags.stream()
                .map(tag -> tag.getTag().getTagName())
                .collect(Collectors.joining(", "));
    }

    public Task getTaskById(long projectId, long taskId){
        Task task = taskRepository.findByProjectIdAndId(projectId, taskId);

        if(task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid task ID");
        }

        return task;
    }

}
