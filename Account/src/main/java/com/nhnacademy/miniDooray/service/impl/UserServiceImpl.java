package com.nhnacademy.miniDooray.service.impl;

import com.nhnacademy.miniDooray.DTO.*;
import com.nhnacademy.miniDooray.entity.User;
import com.nhnacademy.miniDooray.exception.InvalidEmailException;
import com.nhnacademy.miniDooray.exception.PasswordMismatchException;
import com.nhnacademy.miniDooray.exception.UserNotFoundException;
import com.nhnacademy.miniDooray.repository.UserRepository;
import com.nhnacademy.miniDooray.service.UserService;
import com.nhnacademy.miniDooray.util.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//#TODO 1 userId 로 받아오던거 name으로 변경 (id는 db에서 쓰일거, name이 로그인 시 사용하는 id)
//#TODO 2 가입 시 userName 중복 불가 처리

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 가입
    @Override
    public User registUser(UserRegistrationRequest userRequest){
        // User 빈 객체 생성
        User registUser = new User();

        // 1. 이메일 형식 검증 (불필요시 제거)
        if (!EmailValidator.isValidEmail(userRequest.userEmail())) {
            throw new InvalidEmailException(userRequest.userEmail()); // 사용자 정의 예외 던지기
        }
        // 2. 저장 할 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(userRequest.userPassword());

        // 3. repository 조회해서 해당 요청한 userName이 없을 때
        if(userRepository.findByUserName(userRequest.userName()) == null){
            registUser.setUserName(userRequest.userName());
            registUser.setUserEmail(userRequest.userEmail());
            registUser.setUserPassword(encryptedPassword);
        }

        log.info("user save ID: {}", registUser.getUserName());
        return userRepository.save(registUser);
    }


    // userName 으로 조회 후 해당 유저 상태만 업데이트 후 저장
    // 탈퇴 유저 처리
    @Override
    public void withdrawUser(WithdrawRequest withdrawRequest) {
        User withdrawUser = userRepository.findByUserName(withdrawRequest.userName());

        log.info("User withdraw, Name: {}", withdrawRequest.userName());
        withdrawUser.setUserStatus(User.UserStatus.WITHDRAW);
        userRepository.save(withdrawUser);
    }

    @Override
    public Long loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUserName(loginRequest.userName());
        
        // 없는 유저일 때
        if(user == null){
            throw new UserNotFoundException(loginRequest.userName());
        }

        // 암호화 된 password와 요청 된 password 비교
        if(comparePassword(loginRequest.userPassword(), user.getUserPassword())){
            return user.getUserId();
        }else{
            throw new PasswordMismatchException(loginRequest.userName(), loginRequest.userPassword());
        }

    }

    @Override
    public void logoutUser() {
        //

    }


    // 입력된 비밀번호와 암호화 된 비밀번호 비교 (로그인 시 검증용)
    @Override
    public boolean comparePassword(String rawPassword, String storedEncryptedPassword) {
        return passwordEncoder.matches(rawPassword, storedEncryptedPassword);  // 비밀번호 검증
    }


    // ------------------ 아래는 추가 구현사항 --------------------- //

    // 유저 조회
    @Override
    public User getUser(Long userId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException(userId.toString()) );

        log.info("getUser ID: {}" , targetUser.getUserId());
        return targetUser;
    }

    // 모든 유저 조회
    @Override
    public List<User> getAllUsers() {
        log.info("get All users");
        return userRepository.findAll();
    }

    // 유저 정보 수정
    @Override
    public void updateUser(Long userId, UserUpdateRequest userRequest) {
        User updateUser = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException(userRequest.userName()) );

        // 이메일 형식 검증
        if (userRequest.userEmail() != null && !EmailValidator.isValidEmail(userRequest.userEmail())) {
            throw new InvalidEmailException(userRequest.userEmail()); // 사용자 정의 예외 던지기
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(userRequest.userPassword());

        updateUser.setUserName(userRequest.userName());
        updateUser.setUserPassword(encryptedPassword);
        updateUser.setUserEmail(userRequest.userEmail());

        userRepository.save(updateUser);
        log.info("user updated ID: {}" ,updateUser.getUserId());
    }

    // 유저 부분 업데이트
    @Override
    public void partialUpdateUser(Long userId, UserPartialUpdateRequest userRequest) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        // 필드가 null이 아닌 경우에만 업데이트
        if (userRequest.userName() != null) {
            existingUser.setUserName(userRequest.userName());
        }
        if (userRequest.userPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(userRequest.userPassword());
            existingUser.setUserPassword(encryptedPassword);
        }
        if (userRequest.userEmail() != null) {
            // 이메일 형식 검증 (불필요시 제거)
            if (!EmailValidator.isValidEmail(userRequest.userEmail())) {
                throw new InvalidEmailException(userRequest.userEmail()); // 사용자 정의 예외 던지기
            }
            existingUser.setUserEmail(userRequest.userEmail());
        }

        userRepository.save(existingUser);
        log.info("User with ID {} has been partially updated", userId);
    }


}
