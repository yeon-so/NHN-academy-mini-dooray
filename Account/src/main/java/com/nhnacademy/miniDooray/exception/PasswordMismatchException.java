package com.nhnacademy.miniDooray.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String userName, String userPassword) {
        super("ID: " + userName + " PWD: " + userPassword);
    }
}
