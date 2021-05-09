package com.example.springboot.controller;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.Board.BoardService;
import com.example.springboot.service.exception.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/v1/board")
@RestController
@Api(tags = {"3. Board"})
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보를 조회 합니다")
    @GetMapping(value = "/{boardName}")
    public SingleResult<Board> boardInfo(@PathVariable String boardName) {
        return responseService.getSingleResult(boardService.findBoard(boardName));
    }

    @ApiOperation(value = "게시판 글 리스트", notes = "게시판 글 리스트를 조회 합니다")
    @GetMapping(value = "/{boardName}/posts")
    public ListResult<Post> posts(@PathVariable String boardName) {
        return responseService.getListResult(boardService.findPosts(boardName));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판에 글 작성", notes = "게시판에 글을 작성합니다")
    @PostMapping(value = "/{boardName}")
    public SingleResult<Post> createPost(@PathVariable String boardName, @Valid @ModelAttribute CommonParamPost commonParamPost) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return responseService.getSingleResult(boardService.writePost(userId, boardName, commonParamPost));
    }

    @ApiOperation(value = "게시판의 글 상세보기", notes = "게시판의 글을 상세보기 합니다")
    @GetMapping(value = "/post/{postNo}")
    public SingleResult<Post> post(@PathVariable long postNo) {
        return responseService.getSingleResult(boardService.getPost(postNo));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판의 글 수정", notes = "게시판의 글을 수정합니다")
    @PutMapping(value = "/post/{postNo}")
    public SingleResult<Post> modifyPost(@PathVariable long postNo, @Valid @ModelAttribute CommonParamPost commonParamPost) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return responseService.getSingleResult(boardService.updatePost(postNo, userId, commonParamPost));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판의 글 삭제", notes = "게시판의 글을 삭제합니다")
    @DeleteMapping(value = "/post/{postNo}")
    public CommonResult deletePost(@PathVariable long postNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        boardService.deletePost(postNo, userId);
        return responseService.getSuccessResult();
    }
}
