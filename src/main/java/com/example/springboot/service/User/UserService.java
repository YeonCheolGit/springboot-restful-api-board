package com.example.springboot.service.User;

import com.example.springboot.DTO.user.UserRequestDTO;
import com.example.springboot.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

    @Transactional(readOnly = true)
    Optional<User> findByUserNo(long userNo);

    @Transactional(readOnly = true)
    Optional<User> findByUserId(String userId);

    @Transactional()
    User findByUserIdAndToken(String userId, String userName);

    @Transactional(readOnly = true)
    List<User> findAll();

    @Transactional
    User save(User user);

    @Transactional
    void saveDTO(UserRequestDTO userRequestDTO);

    void deleteByUserNo(long userNo);

    Optional<User> findByUserIdAndProvider(String userId, String provider);
}
