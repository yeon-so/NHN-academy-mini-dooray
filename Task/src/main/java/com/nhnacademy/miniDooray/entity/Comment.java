package com.nhnacademy.miniDooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "task_id", foreignKey = @ForeignKey(name = "FK_tasks_TO_comments_1"))
    private Task task;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_users_TO_comments_1"))
    private User user;

    @NotNull
    @Size(max = 255)
    private String content;

    @NotNull
    private LocalDateTime createdAt;

}