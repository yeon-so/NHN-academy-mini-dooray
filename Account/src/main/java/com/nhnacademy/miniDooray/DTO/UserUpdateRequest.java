package com.nhnacademy.miniDooray.DTO;

public record UserUpdateRequest(Long userId, String userName, String userPassword, String userEmail) {
}