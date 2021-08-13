package com.example.springboot.controller;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.DTO.reply.ReplyDTO;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.service.exception.ResponseService;
import com.example.springboot.service.reply.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"5. reply"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final ResponseService responseService;

    @ApiOperation(value = "게시글 댓글 조회", notes = "하나의 게시글의 댓글 조회합니다. 게시물과 댓글 요청 분리합니다.")
    @GetMapping(value = "/{postNo}")
    public ResponseEntity<ListResult<ReplyDTO>> getReplies(@PathVariable long postNo) {
        List<ReplyDTO> replies = replyService.getRepliesByPostNo(postNo);
        return new ResponseEntity<>(responseService.getListResult(replies), HttpStatus.CREATED);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "게시글에 댓글 등록", notes = "하나의 게시물에 댓글을 작성합니다")
    @PostMapping(value = "/{postNo}")
    public ResponseEntity<CommonResult> writeReply(@PathVariable long postNo, @ModelAttribute ParamReply reply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // SecurityContextHolder에 저장된 현재 접속 아이디 입니다.
        replyService.writeReply(postNo,userId, reply);
        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.CREATED);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "게시글의 댓글 수정", notes = "하나의 게시물에 댓글을 수정합니다")
    @PutMapping(value = "/{replyNo}")
    public ResponseEntity<CommonResult> updateReply(@PathVariable long replyNo, @ModelAttribute ParamReply reply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // SecurityContextHolder에 저장된 현재 접속 아이디 입니다.
        replyService.updateReply(replyNo, userId, reply);
        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.OK);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "게시글의 댓글 삭제", notes = "하나의 게시물에 댓글을 삭제합니다")
    @DeleteMapping(value = "/{replyNo}")
    public ResponseEntity<CommonResult> deleteReply(@PathVariable long replyNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // SecurityContextHolder에 저장된 현재 접속 아이디 입니다.
        replyService.deleteReply(replyNo, userId);
        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.OK);
    }
}
