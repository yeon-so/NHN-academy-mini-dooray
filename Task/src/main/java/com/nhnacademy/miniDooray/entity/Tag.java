package com.nhnacademy.miniDooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag {

    public Tag(Project project, String tagName) {
        this.project = project;
        this.tagName = tagName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @NotNull
    @ManyToOne
    @Setter
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "FK_projects_TO_tags_1"))
    private Project project;

    @NotNull
    @Setter
    @Size(min = 1, max = 50)
    @Column(name = "tag_name")
    private String tagName;
}
