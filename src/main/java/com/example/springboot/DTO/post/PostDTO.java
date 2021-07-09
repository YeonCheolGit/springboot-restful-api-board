package com.example.springboot.DTO.post;

import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private long postNo;

    @NotEmpty
    @Length(min = 1, max = 100)
    private String title;

    @NotEmpty
    @Length(min = 1, max = 100)
    private String author;

    @NotEmpty
    @Length(min = 1, max = 255)
    private String content;

    private User userNo;
    private Board boardNo;
    private Set<Reply> replyByPostNo;

    // DTO to Entity
    public Post toEntity() {
        return Post.builder()
                .postNo(this.postNo)
                .title(this.title)
                .author(this.author)
                .content(this.content)
                .userNo(this.userNo)
                .boardNo(this.boardNo)
                .build();
    }

    /*
     1. 게시물 수정 가능한 속성만 정의 합니다.
     2. 속성 정의 후 toEntity()로 실제 DB 업데이트
     */
    public void setUpdate(String author, String title, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }
}
