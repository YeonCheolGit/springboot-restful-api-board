package com.example.springboot.DTO.reply;

import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.entity.User;
import lombok.*;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class ReplyRequestDTO {
    private long replyNo;
    private String content;
    private String author;
    private User userNo;
    private Post postNo;

    public Reply toEntity() {
        return Reply.builder()
                .content(this.content)
                .author(this.author)
                .userNo(this.userNo)
                .postNo(this.postNo)
                .build();
    }
}
