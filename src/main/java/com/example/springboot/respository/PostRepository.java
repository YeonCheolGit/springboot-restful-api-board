package com.example.springboot.respository;

import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByBoardNo(Board board);
}
