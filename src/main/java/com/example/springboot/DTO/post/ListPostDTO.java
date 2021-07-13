package com.example.springboot.DTO.post;

import com.example.springboot.entity.Post;
import com.example.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Referenced Entity: Post
 * PostDTO와 다르게 boardNo 필드 없습니다.
 * 한 개의 게시물을 볼 때 게시판의 정보도 같이 나오는 것을 방지하기 위함 입니다.
 * SinglePostDTO와 다르게 여러개의 게시글을 보여줄 때 사용 합니다.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListPostDTO {
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

    // List<Post> entity to List<postDTO> dto
    public List<ListPostDTO> toListPostDTO(List<Post> posts) {
        return posts.stream().map(Post::toPostDTO).collect(Collectors.toList());
    }
}
