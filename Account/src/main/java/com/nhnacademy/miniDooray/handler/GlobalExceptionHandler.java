package com.nhnacademy.miniDooray.handler;

import com.nhnacademy.miniDooray.exception.InvalidEmailException;
import com.nhnacademy.miniDooray.exception.PasswordMismatchException;
import com.nhnacademy.miniDooray.exception.UserAlreadyExistsException;
import com.nhnacademy.miniDooray.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 사용 할 exception을 추가, 관리하는 곳
@ControllerAdvice
public class GlobalExceptionHandler {

    // body(e.getMessage())에 추가할 사항 있으면 추가하기
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found" + e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("UserAlreadyExists" + e.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handleUserPasswordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password mismatch " + e.getMessage());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException(InvalidEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러: " + e.getMessage());
    }
}
