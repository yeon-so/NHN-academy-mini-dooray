package com.nhnacademy.miniDooray.exception;

// 해당 ID의 User가 존재하지 않을 때 예외처리
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userName) {
        super(String.valueOf(userName));
    }
}
