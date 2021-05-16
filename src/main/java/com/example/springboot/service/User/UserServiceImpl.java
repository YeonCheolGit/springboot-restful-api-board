package com.example.springboot.service.User;

import com.example.springboot.DTO.UserRequestDTO;
import com.example.springboot.advice.exception.DuplicatedUserException;
import com.example.springboot.entity.User;
import com.example.springboot.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUserNo(long userNo) {
        return userRepository.findByUserNo(userNo);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
//
//    @Override
//    public Optional<UserResponseDTO> findByUserId(String userId) {
//        return userRepository.findByUserId(userId);
//    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void saveDTO(UserRequestDTO userRequestDTO) {
        verifyDuplicateEmail(userRequestDTO.getUserId());
        userRequestDTO.setUserPwd(passwordEncoder.encode(userRequestDTO.getUserPwd()));
        userRepository.save(userRequestDTO.toEntity());
    }

    private void verifyDuplicateEmail(String userId) {
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new DuplicatedUserException(userId + " > 이미 가입된 회원 아이디 입니다.");
        }
    }

    @Override
    public void deleteByUserNo(long userNo) {
        userRepository.deleteByUserNo(userNo);
    }

    @Override
    public Optional<User> findByUserIdAndProvider(String userId, String provider) {
        return userRepository.findByUserIdAndProvider(userId, provider);
    }
}
