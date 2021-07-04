package com.example.springboot.controller;


import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.User;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.user.UserService;
import com.example.springboot.service.exception.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. user"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회합니다")
    @GetMapping(value = "/users")
    public ResponseEntity<ListResult<User>> findAllUser() throws FindAnyFailException {
        return new ResponseEntity<>(
                responseService.getListResult(userService.findAll()),
                HttpStatus.OK
        );
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원 한명 조회합니다")
    @GetMapping(value = "/user")
    public ResponseEntity<SingleResult<User>> findUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return new ResponseEntity<>(
                responseService.getSingleResult(userService.findByUserId(userId).orElseThrow(FindAnyFailException::new)),
                HttpStatus.OK
        );
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 이름 수정", notes = "한명의 회원 이름을 수정 합니다")
    @PutMapping(value = "/user")
    public ResponseEntity<SingleResult<User>> modify(@ApiParam(value = "회원이름(userName)", required = true) @RequestParam String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(
                responseService.getSingleResult(userService.findByUserIdAndToken(authentication.getName(), userName)),
                HttpStatus.OK
        );
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 한명의 회원정보를 삭제합니다")
    @DeleteMapping(value = "/user/{userNo}")
    public ResponseEntity<CommonResult> delete(@ApiParam(value = "회원번호", required = true) @PathVariable long userNo) {
        userService.deleteByUserNo(userNo);
        return new ResponseEntity<>(
                responseService.getSuccessDeleted(),
                HttpStatus.OK
        );
    }
}
