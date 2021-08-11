package com.example.springboot.controller;

import com.example.springboot.DTO.post.ListPostDTO;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.service.board.BoardService;
import com.example.springboot.service.exception.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/boardSearch")
@RestController
@Api(tags = {"4. Board Search"})
@RequiredArgsConstructor
@Slf4j
public class BoardSearchController {

    private final BoardService boardService;
    private final ResponseService responseService;


    @ApiOperation(value = "게시판 비슷한 글 조회", notes = "입력된 제목과 비슷한 글 조회합니다.")
    @GetMapping(value = "/post/title/{pageNo}")
    public ResponseEntity<ListResult<ListPostDTO>> findPostByTitleLike(@RequestParam String title, @PathVariable int pageNo) {
        List<ListPostDTO> posts = boardService.findPostByTitleLike(title, pageNo);
        return new ResponseEntity<>(responseService.getListResult(posts), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 일치하는 글 조회", notes = "입력된 작성자와 일치하는 글 조회합니다.")
    @GetMapping(value = "/post/author/{pageNo}")
    public ResponseEntity<ListResult<ListPostDTO>> findPostByAuthor(@RequestParam String author, @PathVariable int pageNo) {
        List<ListPostDTO> posts = boardService.findPostByAuthor(author, pageNo);
        return new ResponseEntity<>(responseService.getListResult(posts), HttpStatus.OK);
    }
}
