package com.nhnacademy.miniDooray.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super("Invalid email format: " + email);
    }
}
