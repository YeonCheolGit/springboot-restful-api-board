package com.example.springbootjpaboard.respository;

import com.example.springbootjpaboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findById(long msrl);
}
