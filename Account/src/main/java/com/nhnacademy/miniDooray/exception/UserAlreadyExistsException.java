package com.nhnacademy.miniDooray.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String userName) {
        super(userName);

    }
}
