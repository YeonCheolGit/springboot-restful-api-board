package com.example.springboot.controller;

import com.example.springboot.advice.exception.DuplicatedUserException;
import com.example.springboot.advice.exception.EmailSignInFailException;
import com.example.springboot.advice.exception.UserNotFoundException;
import com.example.springboot.config.security.JwtTokenProvider;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.model.KakaoProfile;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.social.KakaoService;
import com.example.springboot.service.exception.ResponseService;
import com.example.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

/*
 * 회원 가입, 로그인 컨트롤러
 */
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
@Log4j2
public class SignController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "회원가입", notes = "회원 가입 합니다")
    @PostMapping(value = "/signUp")
    public CommonResult signUp(@ApiParam(value = "회원 아이디 (userId) : 이메일", required = true) @Valid @RequestParam String userId,
                               @ApiParam(value = "회원 비밀번호 (userPwd)", required = true) @Valid @RequestParam String userPwd,
                               @ApiParam(value = "회원 이름 (userName)", required = true) @Valid @RequestParam String userName) {
        Role role = new Role();
        role.setRoleNo(4);
        userService.save(User.builder()
                .userId(userId)
                .userPwd(passwordEncoder.encode(userPwd))
                .userName(userName)
                .roles(Collections.singletonList(role))
                .build()
        );
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "로그인", notes = "회원 로그인 합니다")
    @PostMapping(value = "/signIn")
    public SingleResult<String> signIn(@ApiParam(value = "회원 아이디 (userId): 이메일", required = true) @RequestParam String userId,
                                       @ApiParam(value = "회원 비밀번호 (userPwd)", required = true) @RequestParam String userPwd) {
        User user = userService.findByUserId(userId).orElseThrow(EmailSignInFailException::new);

        if (!passwordEncoder.matches(userPwd, user.getPassword())) {
            throw new EmailSignInFailException();
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserNo()), user.getRoles()));
    }

    // 가지고 온 카카오 Access Token을 이용해서 가입합니다
    @ApiOperation(value = "카카오 계정 가입", notes = "카카오 계정 Access Token 이용 회원가입 합니다")
    @PostMapping(value = "/signUp/{provider}")
    public CommonResult signUpByProvider(@ApiParam(value = "서비스 제공자 (provider)", required = true, defaultValue = "kakao") @PathVariable String provider,
                                         @ApiParam(value = "카카오 토큰 (access_token)", required = true) @RequestParam String accessToken,
                                         @ApiParam(value = "회원 이름 (userName)", required = true) @RequestParam String userName) {
        Role role = new Role();
        role.setRoleNo(4);
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<User> user = userService.findByUserIdAndProvider(String.valueOf(profile.getUserId()), provider);

        if (user.isPresent())
            throw new DuplicatedUserException();

        userService.save(User.builder()
                .userId(String.valueOf(profile.getUserId()))
                .provider(provider)
                .userName(userName)
                .roles(Collections.singletonList(role))
                .build());
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 회원 로그인 한다")
    @PostMapping(value = "/signIn/{provider}")
    public SingleResult<String> signInByProvider(@ApiParam(value = "서비스 제공자 (provider)", required = true, defaultValue = "kakao") @PathVariable String provider,
                                                 @ApiParam(value = "카카오 액세스 토큰 (accessToken)", required = true) @RequestParam String accessToken) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        User user = userService.findByUserIdAndProvider(String.valueOf(profile.getUserId()), provider).orElseThrow(UserNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserNo()), user.getRoles()));
    }
}
