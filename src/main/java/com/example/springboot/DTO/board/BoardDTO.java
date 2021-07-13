package com.example.springboot.DTO.board;

import com.example.springboot.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/*
 * Referenced Entity: Board
 * Board entity 전체 필드 가지고 있습니다.
 */
@Service
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private long boardNo;
    private String name;
    private Set<Post> posts;
}
