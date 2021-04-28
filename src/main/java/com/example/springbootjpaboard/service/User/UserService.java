package com.example.springbootjpaboard.service.User;

import com.example.springbootjpaboard.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User save(User user);

    Optional<User> findById(long msrl);

    void deleteById(long msrl);
}
