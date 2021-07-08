package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
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
    private User userNo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardNo")
    private Board boardNo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "postNo")
    private Set<Reply> reply_postNo;

//    public Post(User userNo, Board boardNo, String author, String title, String content) {
//        this.title = title;
//        this.author = author;
//        this.content = content;
//        this.userNo = userNo;
//        this.boardNo = boardNo;
//    }

//     JPA dirty checking으로 인한 업데이트
    public void setUpdate(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
