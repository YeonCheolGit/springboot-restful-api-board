package com.example.springboot.entity;

import com.example.springboot.DTO.board.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "board")
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardNo;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardNo")
    private Set<Post> posts;

    public static BoardDTO toDTO(Board board) {
        new BoardDTO();
        return BoardDTO.builder()
                .boardNo(board.getBoardNo())
                .name(board.getName())
                .posts(board.getPosts())
                .build();
    }
}
