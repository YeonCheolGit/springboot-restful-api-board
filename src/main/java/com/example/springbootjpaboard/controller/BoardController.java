package com.example.springbootjpaboard.controller;

import com.example.springbootjpaboard.domain.Board;
import com.example.springbootjpaboard.service.BoardService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /*
     * 전체 글 목록 조회
     */
    @GetMapping("/boards")
    public List<Board> allBoards() {
        return boardService.findAll();
    }

    /*
     * 새 글 생성
     */
    @PostMapping("/board")
    public Object createNewBoard(@RequestBody Board board) {
        return boardService.save(board);
    }

    /*
     * 글 상세 보기
     */
    @GetMapping("/board/{id}")
    public Object getBoard(@RequestBody @PathVariable Long id) {
        return boardService.findById(id);
    }


    /**
     * 글 수정
     */
    @PutMapping("/board/{id}")
    public Board updateBoard(@PathVariable Long id,
                            @Valid @RequestBody Board newBoard) {
        return boardService.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardService.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return boardService.save(newBoard);
                });
    }

    /*
     * 글 삭제
     */
    @DeleteMapping("/board/{id}")
    public void deleteBoard(@RequestBody @PathVariable Long id) {
        boardService.deleteById(id);
    }
}
