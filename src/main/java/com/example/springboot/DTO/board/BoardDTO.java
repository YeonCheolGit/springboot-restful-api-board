package com.example.springboot.DTO.board;

import com.example.springboot.entity.Post;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private long boardNo;

    private String name;

    private Set<Post> posts;
}
