package com.example.springboot.respository;

import com.example.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findById(long msrl);
}
