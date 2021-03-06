package com.example.springboot.DTO.post;

import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/*
 * Referenced Entity: Post
 * Post entity 필드 전체 참조 합니다.
 */
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

    private User user;
    private Board board;

    /*
     1. 게시물 수정 가능한 속성만 정의 합니다.
     2. 속성 정의 후 toEntity()로 실제 DB 업데이트
     */
    public void setUpdate(String author, String title, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    // PostDTO(DTO) to Post(entity)
    public Post toRequestPostEntity(PostDTO postDTO) {
        return Post.builder()
                .postNo(postDTO.getPostNo())
                .title(postDTO.getTitle())
                .author(postDTO.getAuthor())
                .content(postDTO.getContent())
                .user(postDTO.getUser())
                .board(postDTO.getBoard())
                .build();
    }

    // Post(entity) to PostDTO(DTO)
    public PostDTO toResponsePostDTO(Post post) {
        return PostDTO.builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .author(post.getAuthor())
                .user(post.getUser())
                .board(post.getBoard())
                .build();
    }
}
