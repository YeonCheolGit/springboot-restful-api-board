package com.example.springboot.respository;

import com.example.springboot.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<Reply> findByReplyNo(Long replyNo);

    @Modifying
    @Query("delete from Reply r where r.post.postNo=:postNo")
    void deleteRepliesByPostNo(@Param("postNo") long postNo);

    @Query("from Reply r where r.post.postNo=:postNo")
    Optional<List<Reply>> getRepliesByPostNo(@Param("postNo") long postNo);
}
