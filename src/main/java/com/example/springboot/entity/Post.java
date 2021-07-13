package com.example.springboot.entity;

import com.example.springboot.DTO.post.ListPostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "post")
public class Post implements Serializable {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private User user; // 게시물 작성한 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardNo")
    private Board board; // 게시물 작성된 게시판

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<Reply> replyByPostNo; // 게시물의 댓글들

    public static ListPostDTO toPostDTO(Post post) {
        new ListPostDTO();
        return ListPostDTO.builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .author(post.getAuthor())
                .content(post.getContent())
                .user(post.getUser())
                .build();
    }
}
