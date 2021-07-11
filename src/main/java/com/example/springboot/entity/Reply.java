package com.example.springboot.entity;

import com.example.springboot.DTO.reply.ReplyDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postNo")
    private Post postNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private User userNo;
}
