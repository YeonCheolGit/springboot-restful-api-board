package com.example.springboot.respository;

import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostNo(long postNo);

    @Query("from Post p where p.board=:board")
    Optional<List<Post>> findPostByBoard(@Param("board")Board board, Pageable pageable);

    @Query("from Post p where p.author=:author")
    Optional<List<Post>> findPostByAuthor(@Param("author")String author, Pageable pageable);

    @Query("FROM Post p WHERE p.title LIKE CONCAT('%', :title, '%')")
    Optional<List<Post>> findPostByTitleLike(@Param("title") String title, Pageable pageable);

//    @Modifying
//    @Query("UPDATE Post p SET p.viewCount = p.viewCount+1 where p.postNo=:postNo")
//    void updateViewCount(@Param("postNo") long postNo);
}
