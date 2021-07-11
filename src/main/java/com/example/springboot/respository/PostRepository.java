package com.example.springboot.respository;

import com.example.springboot.DTO.board.BoardDTO;
import com.example.springboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findByBoardNo(BoardDTO board);

    Optional<Post> findByPostNo(long postNo);
}
