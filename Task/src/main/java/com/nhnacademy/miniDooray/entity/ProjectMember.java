package com.nhnacademy.miniDooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_members")
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_users_TO_project_members_1"))
    @Setter
    private User member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "FK_projects_TO_project_members_1"))
    @Setter
    private Project project;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Setter
    private Role memberRole;

    public enum Role {
        ADMIN, MEMBER
    }
}
