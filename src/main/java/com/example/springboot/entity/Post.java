package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
    private User userNo; // 게시물 작성한 유저

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardNo")
    private Board boardNo; // 게시물 작성된 게시판

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postNo")
    private Set<Reply> replyByPostNo; // 게시물의 댓글들
}
