package com.example.springboot.entity;

import com.example.springboot.DTO.reply.ReplyDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reply")
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString(exclude = {"user", "post"})
public class Reply implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long replyNo;
    @Column
    private String content;
    @Column
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postNo")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    public static ReplyDTO toResponseReplyDTO(Reply replies) {
        return ReplyDTO.builder()
                .replyNo(replies.getReplyNo())
                .content(replies.getContent())
                .author(replies.getAuthor())
                .build();
    }
}
