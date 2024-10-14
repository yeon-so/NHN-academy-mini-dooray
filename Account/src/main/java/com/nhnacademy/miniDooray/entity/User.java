package com.nhnacademy.miniDooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

/*
    Users의 Entity 클래스입니다.
    1. 생성자 3개: AllArgs, NoArgs, UserStatus를 기본값(REGISTER)으로 받는 생성자
    2. 각 변수들은 DB 저장 시 _(언더바) 규칙으로 저장 됩니다.
    3. 해당 Entity의 식별은 userId로 진행하며 객체 생성 시 자동으로 생성되는 전략을 사용합니다.

*/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    // UserStatus Default 값 사용할 때 생성자
    public User(String userName, String userPassword, String userEmail){
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        setUserStatus(UserStatus.REGISTER);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "user_password")
    private String userPassword;


    @Email // 이메일 형식 검증
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus = UserStatus.REGISTER;



    // 유저 상태 enum 값
    public enum UserStatus {
        REGISTER,  // 가입 상태
        SLEEP,     // 휴면 상태
        WITHDRAW   // 탈퇴 상태
    }
}
