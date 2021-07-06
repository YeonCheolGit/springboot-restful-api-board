package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "Reply")
public class Reply {

    @Id
    private long replyNo;
    @Column
    private String content;
    @Column
    private String author;

    @ManyToOne
    @JoinColumn(name = "postNo")
    private Post postNo;

    @ManyToOne
    @JoinColumn(name = "userNo")
    private User userNo;

    public void setUpdate(String content) {
        this.content = content;
    }
}
