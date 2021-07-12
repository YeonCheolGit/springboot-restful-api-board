package com.example.springboot.DTO.board;

import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private long boardNo;
    private String name;
    private Set<Post> posts;

    public BoardDTO toBoardDTO(Board board) {
        return BoardDTO.builder()
                .boardNo(board.getBoardNo())
                .name(board.getName())
                .build();
    }

    public List<BoardDTO> toListBoardDTO(List<Board> boards) {
        return boards.stream().map(Board::toDTO).collect(Collectors.toList());
    }

    public Board toBoardEntity(BoardDTO boardDTO) {
        return Board.builder()
                .boardNo(boardDTO.getBoardNo())
                .name(boardDTO.getName())
                .build();
    }
}
