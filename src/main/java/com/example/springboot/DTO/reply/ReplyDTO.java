package com.example.springboot.DTO.reply;

import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/*
 * Referenced Entity: Reply
 * Reply entity 전체 필드를 참조 합니다.
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReplyDTO implements Serializable {

    private long replyNo;

    @NotEmpty
    @Length(min = 1, max = 255)
    private String content;

    @NotEmpty
    @Length(min = 1, max = 50)
    private String author;

    private User user;
    private Post post;

    public void setUpdate(String content) {
        this.content = content;
    }

    // Reply(entity) to ReplyDTO(DTO)
    public ReplyDTO toReplyDTO(Reply reply) {
        return ReplyDTO.builder()
                .replyNo(reply.getReplyNo())
                .content(reply.getContent())
                .author(reply.getAuthor())
                .user(reply.getUser())
                .post(reply.getPost())
                .build();
    }

    // ReplyDTO(DTO) to Reply(entity)
    public Reply toReplyEntity(ReplyDTO replyDTO) {
        return Reply.builder()
                .replyNo(replyDTO.getReplyNo())
                .content(replyDTO.getContent())
                .author(replyDTO.getAuthor())
                .post(replyDTO.getPost())
                .user(replyDTO.getUser())
                .build();
    }
}
