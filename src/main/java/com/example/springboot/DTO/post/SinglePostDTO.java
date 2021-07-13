package com.example.springboot.DTO.post;

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
    private Set<Reply> replyByPostNo;

    // Post(entity) to RequestSinglePostDTO(DTO)
    public SinglePostDTO requestSinglePostDTO(Post post) {
        return SinglePostDTO.builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .author(post.getAuthor())
                .user(post.getUser())
                .replyByPostNo(post.getReplyByPostNo())
                .build();
    }

    // RequestSinglePostDTO(DTO) to Post (entity)
    public Post toPostEntity(SinglePostDTO singlePostDTO) {
        return Post.builder()
                .postNo(singlePostDTO.getPostNo())
                .title(singlePostDTO.getTitle())
                .author(singlePostDTO.getAuthor())
                .content(singlePostDTO.getContent())
                .user(singlePostDTO.getUser())
                .replyByPostNo(singlePostDTO.getReplyByPostNo())
                .build();
    }
}
