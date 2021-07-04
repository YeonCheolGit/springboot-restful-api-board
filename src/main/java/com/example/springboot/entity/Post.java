package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postNo;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 500)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardNo")
    private Board boardNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userNo")
    private User userNo;

    public Post(User userNo, Board boardNo, String author, String title, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.userNo = userNo;
        this.boardNo = boardNo;
    }

    // JPA dirty checking으로 인한 업데이트
    public void setUpdate(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
