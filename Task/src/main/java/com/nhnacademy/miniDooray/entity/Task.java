package com.nhnacademy.miniDooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tasks")
public class Task {

    public Task(Project project, Milestone milestone, User user, String taskName, String taskContent, LocalDateTime createdAt) {
        this.project = project;
        this.milestone = milestone;
        this.user = user;
        this.taskName = taskName;
        this.taskContent = taskContent;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "FK_projects_TO_tasks_1"))
    private Project project;

    @ManyToOne
    @JoinColumn(name = "milestone_id", foreignKey = @ForeignKey(name = "FK_milestones_TO_tasks_1"))
    private Milestone milestone;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_users_TO_tasks_1"))
    private User user;

    @NotNull
    @Size(min = 1, max = 50, message = "Task name must be between 1 and 50 characters")
    private String taskName;

    @Size(max = 255)
    private String taskContent;

    private LocalDateTime createdAt;
}
