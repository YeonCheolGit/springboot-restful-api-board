package com.example.springboot.DTO.reply;

import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class ReplyDTO implements Serializable {

    private long replyNo;

    @NotEmpty
    @Length(min = 1, max = 255)
    private String content;

    @NotEmpty
    @Length(min = 1, max = 50)
    private String author;

    private User userNo;
    private Post postNo;

    public Reply toEntity() { // DTO to Entity
        return Reply.builder()
                .replyNo(this.replyNo)
                .content(this.content)
                .author(this.author)
                .userNo(this.userNo)
                .postNo(this.postNo)
                .build();
    }

    public void setUpdate(String content) {
        this.content = content;
    }
}
