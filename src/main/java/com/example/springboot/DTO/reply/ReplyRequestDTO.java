package com.example.springboot.DTO.reply;

import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class ReplyRequestDTO {

    private long replyNo;

    @NotEmpty
    @Length(min = 1, max = 255)
    private String content;

    @NotEmpty
    @Length(min = 1, max = 50)
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
