package com.nhnacademy.miniDooray.controller;

import com.nhnacademy.miniDooray.DTO.*;
import com.nhnacademy.miniDooray.entity.User;
import com.nhnacademy.miniDooray.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    User 엔티티의 컨트롤러입니다.(API)
    기능
    POST    / 유저 가입
    GET     / 전체 유저 정보 가져오기
    GET     / 단일 유저 정보 가져오기
    PUT     / 유저 수정 (새로 대체함)
    DELETE  / 유저 삭제
    PATCH   / 유저 일부 업데이트
    
    로그인, 로그아웃 추가하기
    +

 */

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    // 등록 요청 -> UserName 중복 확인 -> 새로운 유저 생성, 저장 -> 생성 완료 HTTP코드와 "OK" 문자열 반환
    // 사용자 등록
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDTO> registUser(@RequestBody UserRegistrationRequest userRequest){
        User newUser = userService.registUser(userRequest); // 중복 시 UserAlredyExites 예외 처리 됨
        ResponseDTO responseDTO = new ResponseDTO("201", "Created");
        //HTTP 상태코드 201
        log.info("registerUser() User 추가 됨.");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // 로그인 요청 -> id, pwd로 존재하는 유저인지, id, pwd가 일치 하는지 확인 후 OK, userName(사용자ID) 반환
    // 사용자 로그인 / 상태코드, userId 반환(Long 타입)
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginRequest loginRequest){
        Long response = userService.loginUser(loginRequest);

        ResponseDTO responseDTO = new ResponseDTO("200", response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // 탈퇴 요청 -> userName(사용자ID) 로 조회, 상태 변경 완료 후 OK 반환
    // 사용자 탈퇴처리 (상태 업데이트)
    @PatchMapping("/withdraw")
    public ResponseEntity<ResponseDTO> withdrawUser(@RequestBody WithdrawRequest withdrawRequest){
        ResponseDTO responseDTO = new ResponseDTO("200", "Withdraw");
        userService.withdrawUser(withdrawRequest);    //서비스 수정
        return ResponseEntity.ok().body(responseDTO);
    }

    // ?
    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logoutUser(){
        ResponseDTO responseDTO = new ResponseDTO("200", "Logout");
        return ResponseEntity.ok().body(responseDTO);
    }

    // ------------------ 아래는 추가 구현사항 --------------------- //



    // 단일 사용자 조회
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        User targetUser = userService.getUser(userId);

        // HTTP 200, user 반환
        log.info("getUser() 단일 User 조회.");
        return ResponseEntity.ok(targetUser);
    }

    // 전체 사용자 조회
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        
        // HTTP 200, User List 반환
        return ResponseEntity.ok(users);
    }

    // 사용자 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userRequest){
        userService.updateUser(userId, userRequest);

        return ResponseEntity.ok().build();
    }

    // 사용자 부분 업데이트
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> partialUpdateUser(@PathVariable Long userId, @RequestBody UserPartialUpdateRequest userRequest) {
        userService.partialUpdateUser(userId, userRequest);
        // HTTP 204 (No Content) 반환
        return ResponseEntity.noContent().build();
    }


//    public ResponseEntity<Void> deleteUser(@RequestBody UserWithdrawRequest){
}
