package com.example.springboot.respository;

import com.example.springboot.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNo(long userNo);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUserId(String userId);

    @EntityGraph(attributePaths = "roles")
    @Query("from User u")
    Optional<List<User>> findAllUsers();

    void deleteByUserNo(long userNo);

    // Provider(소셜 계정)으로
    Optional<User> findByUserIdAndProvider(String userId, String provider);
}
