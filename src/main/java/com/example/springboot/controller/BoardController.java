package com.example.springboot.controller;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.DTO.board.OnlyBoardDTO;
import com.example.springboot.DTO.post.ListPostDTO;
import com.example.springboot.DTO.post.SinglePostDTO;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.board.BoardService;
import com.example.springboot.service.exception.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping(value = "/api/v1/board")
@RestController
@Api(tags = {"3. Board"})
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보를 조회 합니다")
    @GetMapping(value = "/{boardName}")
    public ResponseEntity<SingleResult<OnlyBoardDTO>> boardInfo(@PathVariable String boardName) {
        OnlyBoardDTO board = boardService.findBoardDTO(boardName);
        return new ResponseEntity<>(responseService.getSingleResult(board), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 글 리스트", notes = "게시판 글 리스트를 조회 합니다")
    @GetMapping(value = "/{boardName}/posts/{pageNo}")
    public ResponseEntity<ListResult<ListPostDTO>> posts(@PathVariable String boardName, @PathVariable int pageNo) {
        List<ListPostDTO> posts = boardService.findPosts(boardName, pageNo);
        return new ResponseEntity<>(responseService.getListResult(posts), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판 글 작성", notes = "게시판에 글을 작성합니다")
    @PostMapping(value = "/{boardName}/post")
    public ResponseEntity<CommonResult> createPost(@PathVariable String boardName, @ModelAttribute @Valid CommonParamPost commonParamPost) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        boardService.writePost(userId, boardName, commonParamPost);
        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "게시판 글 상세보기", notes = "게시판의 글을 상세보기 합니다. 게시물과 댓글 요청 분리합니다.")
    @GetMapping(value = "/post/{postNo}")
    public ResponseEntity<SingleResult<SinglePostDTO>> post(@PathVariable long postNo) throws ExecutionException, InterruptedException {
        SinglePostDTO singleResult = boardService.getPost(postNo);
        return new ResponseEntity<>(responseService.getSingleResult(singleResult), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판 글 수정", notes = "게시판의 글을 수정합니다")
    @PutMapping(value = "/post/{postNo}")
    public ResponseEntity<CommonResult> modifyPost(@PathVariable long postNo, @ModelAttribute @Valid CommonParamPost commonParamPost) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        boardService.updatePost(postNo, userId, commonParamPost);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판 글 삭제", notes = "게시판의 글을 삭제합니다")
    @DeleteMapping(value = "/post/{postNo}")
    public ResponseEntity<CommonResult> deletePost(@PathVariable long postNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        boardService.deletePost(postNo, userId);
        return new ResponseEntity<>(responseService.getSuccessDeleted(), HttpStatus.OK);
    }
}
