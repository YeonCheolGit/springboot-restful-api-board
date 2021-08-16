package com.example.springboot.DTO.post;

import com.example.springboot.entity.Post;
import com.example.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/*
 * Referenced Entity: Post
 * PostDTO와 다르게 boardNo 필드 없습니다.
 * 한 개의 게시물을 볼 때 게시판의 정보도 같이 나오는 것을 방지하기 위함 입니다.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SinglePostDTO {
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

    // Post(entity) to toResponseSinglePostDTO(DTO)
    public SinglePostDTO toResponseSinglePostDTO(Post post) {
        return SinglePostDTO.builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .user(post.getUser())
                .build();
    }

    // toRequestPostEntity(DTO) to Post (entity)
    public Post toRequestPostEntity(SinglePostDTO singlePostDTO) {
        return Post.builder()
                .postNo(singlePostDTO.getPostNo())
                .title(singlePostDTO.getTitle())
                .author(singlePostDTO.getAuthor())
                .content(singlePostDTO.getContent())
                .user(singlePostDTO.getUser())
                .build();
    }
}
