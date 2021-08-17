package com.example.springboot.service.user;

import com.example.springboot.DTO.user.KakaoUserRequestDTO;
import com.example.springboot.DTO.user.UserRequestDTO;
import com.example.springboot.advice.exception.DuplicatedDataException;
import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.User;
import com.example.springboot.respository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUserNo(long userNo) {
        return userRepository.findByUserNo(userNo);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public User findByUserIdAndToken(String userId, String newUserName) {
        User user = userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new);
        return userRepository.save(user.updateUserName(newUserName));
    }

    public List<User> findAll() {
        return userRepository.findAllUsers().orElseThrow(FindAnyFailException::new);
    }

    public void save(KakaoUserRequestDTO kakaoUserRequestDTO) {
        userRepository.save(kakaoUserRequestDTO.toEntity());
    }

    public void saveEmailUser(UserRequestDTO userRequestDTO) {
        verifyDuplicateEmail(userRequestDTO.getUserId());
        userRequestDTO.setUserPwd(passwordEncoder.encode(userRequestDTO.getUserPwd()));
        userRepository.save(userRequestDTO.toEntity());
    }

    private void verifyDuplicateEmail(String userId) {
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new DuplicatedDataException(userId + " > 이미 가입된 회원 아이디 입니다.");
        }
    }

    public void deleteByUserNo(long userNo) {
        userRepository.deleteByUserNo(userNo);
    }

    public Optional<User> findByUserIdAndProvider(String userId, String provider) {
        return userRepository.findByUserIdAndProvider(userId, provider);
    }
}
