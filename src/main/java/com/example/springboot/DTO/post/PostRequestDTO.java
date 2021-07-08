package com.example.springboot.DTO.post;

import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {

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

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .author(this.author)
                .content(this.content)
                .userNo(this.userNo)
                .boardNo(this.boardNo)
                .build();
    }

//    public Post setUpdate() {
//        return Post.builder()
//                .postNo(this.postNo.getPostNo())
//                .title(this.title)
//                .author(this.author)
//                .content(this.content)
//                .build();
//    }
}
