package com.example.springboot.entity;

import com.example.springboot.DTO.post.PostDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "post")
public class Post extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postNo;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userNo")
    private User userNo; // 게시물 작성한 유저

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardNo")
    private Board boardNo; // 게시물 작성된 게시판

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postNo")
    private Set<Reply> replyByPostNo; // 게시물의 댓글들

    // Entity to DTO
    public PostDTO toDTO() {
        return PostDTO.builder()
                .postNo(this.postNo)
                .title(this.title)
                .author(this.author)
                .content(this.content)
                .userNo(this.userNo)
                .boardNo(this.boardNo)
                .replyByPostNo(this.replyByPostNo)
                .build();
    }
}
