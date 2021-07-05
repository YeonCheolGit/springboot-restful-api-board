package com.example.springboot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Reply")
public class Reply {

    @Id
    private long replyNo;
    @Column
    private String content;
    @Column
    private String author;
    @Column
    private long postNo;

    public Reply(String content, String author, long postNo) {
        this.content = content;
        this.author = author;
        this.postNo = postNo;
    }
}
