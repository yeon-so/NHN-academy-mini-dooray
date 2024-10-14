package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.DTO.*;
import com.nhnacademy.miniDooray.entity.User;

import java.util.List;

public interface UserService {
    User registUser(UserRegistrationRequest userRequest);
    void withdrawUser(WithdrawRequest withdrawRequest);
    Long loginUser(LoginRequest loginRequest);
    void logoutUser();
    boolean comparePassword(String rawPassword, String storedEncryptedPassword);

    // ------------------ 아래는 추가 구현사항 --------------------- //
    User getUser(Long userId);
    List<User> getAllUsers();
    void updateUser(Long userId, UserUpdateRequest userRequest);
    void partialUpdateUser(Long userId, UserPartialUpdateRequest userRequest);

}
