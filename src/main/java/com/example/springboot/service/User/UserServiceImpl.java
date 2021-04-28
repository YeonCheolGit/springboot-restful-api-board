package com.example.springboot.service.User;

import com.example.springboot.entity.User;
import com.example.springboot.respository.UserJpaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<User> findById(long msrl) {
        return userJpaRepo.findById(msrl);
    }

    @Override
    public void deleteById(long msrl) {
        userJpaRepo.deleteById(msrl);
    }
}
