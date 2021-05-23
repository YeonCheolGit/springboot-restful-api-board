package com.example.springboot.respository;

import com.example.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNo(long userNo);

    Optional<User> findByUserId(String userId);

    List<User> findAll();

    User save(User user);

    @Transactional
    void deleteByUserNo(long userNo);

    // Provider(소셜 계정)으로
    Optional<User> findByUserIdAndProvider(String userId, String provider);

}
