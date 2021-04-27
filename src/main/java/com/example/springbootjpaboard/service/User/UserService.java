package com.example.springbootjpaboard.service.User;

import com.example.springbootjpaboard.domain.User;
import com.example.springbootjpaboard.respository.UserJpaRepo;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User save(User user);
}
