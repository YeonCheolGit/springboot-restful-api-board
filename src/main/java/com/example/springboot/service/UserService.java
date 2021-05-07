package com.example.springboot.service;

import com.example.springboot.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

    @Transactional(readOnly = true)
    Optional<User> findByUserNo(long userNo);

    @Transactional(readOnly = true)
    Optional<User> findByUserId(String userId);

    @Transactional(readOnly = true)
    List<User> findAll();

    @Transactional
    User save(User user);

    void deleteByUserNo(long userNo);

    Optional<User> findByUserIdAndProvider(String userId, String provider);
}
