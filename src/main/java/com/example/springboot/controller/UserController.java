package com.example.springboot.controller;


import com.example.springboot.advice.exception.UserNotFoundException;
import com.example.springboot.entity.User;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.respository.UserRepository;
import com.example.springboot.service.ResponseService;
import com.example.springboot.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 * 회원 조회, 수정, 삭제 컨트롤러
 */
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
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() throws UserNotFoundException {
        return responseService.getListResult(userService.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원 한명 조회합니다.")
    @GetMapping(value = "/users/{userId}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원ID : Email", required = true) @PathVariable long userId) {
        return responseService.getSingleResult(userService.findByUserNo(userId).orElseThrow(UserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "한명의 회원정보를 수정 합니다.")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam long userNo,
            @ApiParam(value = "회원아이디", required = true) @RequestParam String userId,
            @ApiParam(value = "회원이름", required = true) @RequestParam String userName) {
        User user = User.builder()
                .userNo(userNo)
                .userId(userId)
                .userName(userName)
                .build();
        return responseService.getSingleResult(userService.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 한명의 회원정보를 삭제합니다.")
    @DeleteMapping(value = "/user/{userNo}")
    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable long userNo) {
        userService.deleteByUserNo(userNo);
        return responseService.getSuccessResult();
    }
}
