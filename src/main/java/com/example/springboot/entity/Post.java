package com.example.springboot.entity;

import com.example.springboot.DTO.kafka.PostViewCountDTO;
import com.example.springboot.DTO.post.ListPostDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString(exclude = {"user", "board"})
public class Post extends BaseTime implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postNo;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String content;

    @Column
    private long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private User user; // 게시물 작성한 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardNo")
    private Board board; // 게시물 작성된 게시판

    public static ListPostDTO toResponsePostDTO(Post post) {
        return ListPostDTO.builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .author(post.getAuthor())
                .content(post.getContent())
                .userId(post.getUser().getUserId())
                .build();
    }

    public static PostViewCountDTO toRequest_PostViewCount_DTO(Post post) {
        return PostViewCountDTO.builder()
                .postNo(post.getPostNo())
                .build();
    }
}
