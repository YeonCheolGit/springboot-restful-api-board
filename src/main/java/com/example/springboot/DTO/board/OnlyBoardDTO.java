package com.example.springboot.DTO.board;

import com.example.springboot.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * Referenced Entity: Board
 * BoardDTO 다르게 Posts 필드 없습니다.
 * 게시판에 참조된 게시글이 아닌, 오직 게시판의 정보만 가지고 옵니다.
 */
@Service
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnlyBoardDTO {

    private long boardNo;
    private String name;

    // Board entity to OnlyBoardDTO
    public OnlyBoardDTO toOnlyBoardDTO(Board board) {
        return OnlyBoardDTO.builder()
                .boardNo(board.getBoardNo())
                .name(board.getName())
                .build();
    }
    // OnlyBoardDTO to Board Entity
    public Board toBoardEntity(OnlyBoardDTO boardDTO) {
        return Board.builder()
                .boardNo(boardDTO.getBoardNo())
                .name(boardDTO.getName())
                .build();
    }
}
