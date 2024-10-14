package com.nhnacademy.miniDooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Setter
    private String projectName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Setter
    private ProjectStatus projectStatus;

    public enum ProjectStatus {
        ACTIVE,
        SLEEP,
        END
    }
}