package com.example.springboot.controller;

import com.example.springboot.advice.exception.EmailSignInFailException;
import com.example.springboot.config.security.JwtTokenProvider;
import com.example.springboot.entity.User;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.respository.UserJpaRepo;
import com.example.springboot.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/*
 * 회원 가입, 로그인 컨트롤러
 */
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "회원 로그인 한다")
    @PostMapping(value = "/signIn")
    public SingleResult<String> signIn(@ApiParam(value = "회원 ID : 이메일", required = true) @RequestParam String userId,
                                       @ApiParam(value = "회원 PW", required = true) @RequestParam String userPwd) {
        User user = userJpaRepo.findByUserId(userId).orElseThrow(EmailSignInFailException::new);

        if (!passwordEncoder.matches(userPwd, user.getPassword())) {
            throw new EmailSignInFailException();
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserNo()), user.getRoles()));
    }

    @ApiOperation(value = "회원가입", notes = "회원 가입 한다")
    @PostMapping(value = "/signUp")
    public CommonResult signUp(@ApiParam(value = "회원 ID : 이메일", required = true) @RequestParam String userId,
                                       @ApiParam(value = "회원 PW", required = true) @RequestParam String userPwd,
                                       @ApiParam(value = "회원 이름", required = true) @RequestParam String userName) {
        userJpaRepo.save(User.builder()
                .userId(userId)
                .userPwd(passwordEncoder.encode(userPwd))
                .userName(userName)
                .roles(Collections.singletonList("ROLE_USER"))
                .build()
        );
        return responseService.getSuccessResult();
    }
}
