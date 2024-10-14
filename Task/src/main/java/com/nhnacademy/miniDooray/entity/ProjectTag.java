package com.nhnacademy.miniDooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_tags")
public class ProjectTag {

    public ProjectTag(Tag tag, Task task) {
        this.tag = tag;
        this.task = task;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_tag_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "FK_tags_TO_project_tags_1"))
    private Tag tag;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "task_id", foreignKey = @ForeignKey(name = "FK_tasks_TO_project_tags_1"))
    private Task task;
}