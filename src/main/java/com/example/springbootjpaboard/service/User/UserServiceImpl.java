package com.example.springbootjpaboard.service.User;

import com.example.springbootjpaboard.domain.User;
import com.example.springbootjpaboard.respository.UserJpaRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepo userJpaRepo;

    public UserServiceImpl (UserJpaRepo userJpaRepo) {
        this.userJpaRepo = userJpaRepo;
    }

    @Override
    public List<User> findAll() {
        return userJpaRepo.findAll();
    }

    @Override
    public User save(User user) {
        return userJpaRepo.save(user);
    }
}
