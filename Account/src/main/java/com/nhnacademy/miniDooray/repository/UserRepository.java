package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);   //임시 userName으로 조회
}
