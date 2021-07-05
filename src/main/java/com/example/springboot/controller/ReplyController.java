package com.example.springboot.controller;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.exception.ResponseService;
import com.example.springboot.service.reply.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3. reply"})
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
public class ReplyController {

    private final ReplyService replyService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급된 Access Token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 등록", notes = "댓글 등록")
    @PostMapping(value = "/{postNo}/reply")
    public ResponseEntity<CommonResult> reply(@PathVariable long postNo, @ModelAttribute ParamReply reply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        replyService.writeReply(userId, postNo, reply);
        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.CREATED);
    }
}
