package com.example.springboot.controller;

import com.example.springboot.DTO.user.UserRequestDTO;
import com.example.springboot.advice.exception.AuthFailException;
import com.example.springboot.advice.exception.DuplicatedDataException;
import com.example.springboot.advice.exception.EmailSignInFailException;
import com.example.springboot.config.security.JwtTokenProvider;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.model.KakaoProfile;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.User.UserService;
import com.example.springboot.service.exception.ResponseService;
import com.example.springboot.service.social.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

// 회원 로그인, 가입 관련 컨트롤러
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class SignController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    /*
     * 회원가입 합니다.
     * DTO 거쳐서 Entity 접근 합니다.
     */
    @ApiOperation(value = "회원가입", notes = "회원 가입 합니다")
    @PostMapping(value = "/signUp")
    public ResponseEntity<CommonResult> signUp(@ModelAttribute @Valid UserRequestDTO userRequestDTO) {
        userService.saveEmailUser(userRequestDTO);
        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "로그인", notes = "회원 로그인 합니다")
    @PostMapping(value = "/signIn")
    public ResponseEntity<CommonResult> signIn(@ApiParam(value = "회원 아이디 (userId): 이메일", required = true) @RequestParam String userId,
                               @ApiParam(value = "회원 비밀번호 (userPwd)", required = true) @RequestParam String userPwd) {
        User user = userService.findByUserId(userId).orElseThrow(EmailSignInFailException::new);

        if (!passwordEncoder.matches(userPwd, user.getPassword())) {
            throw new EmailSignInFailException(userId + " > 입력된 비밀번호가 일치하지 않습니다.");
        }
        return new ResponseEntity<>(responseService.getSingleResult(
                jwtTokenProvider.createToken(String.valueOf(user.getUserNo()), user.getRoles())), HttpStatus.OK);
    }

    // 가지고 온 카카오 Access Token을 이용해서 가입합니다
    @ApiOperation(value = "카카오 계정 가입", notes = "카카오 계정 Access Token 이용 회원가입 합니다")
    @PostMapping(value = "/signUp/{provider}")
    public ResponseEntity<CommonResult> signUpByProvider(@ApiParam(value = "서비스 제공자 (provider)", required = true, defaultValue = "kakao") @PathVariable String provider,
                                         @ApiParam(value = "카카오 토큰 (access_token)", required = true) @RequestParam String accessToken,
                                         @ApiParam(value = "회원 이름 (userName)", required = true) @RequestParam String userName) {
        Role role = new Role();
        role.setRoleNo(1);

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);

        Optional<User> user = userService.findByUserIdAndProvider(String.valueOf(profile.getKakao_account().getEmail()), provider);
        if (user.isPresent())
            throw new DuplicatedDataException("중복된 회원 이메일 입니다.");

        userService.save(User.builder()
                .userId(String.valueOf(profile.getKakao_account().getEmail()))
                .provider(provider)
                .userName(userName)
                .roles(Collections.singleton(role))
                .build());
        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 회원 로그인 합니다")
    @PostMapping(value = "/signIn/{provider}")
    public ResponseEntity<SingleResult<String>> signInByProvider(@ApiParam(value = "서비스 제공자 (provider)", required = true, defaultValue = "kakao") @PathVariable String provider,
                                                 @ApiParam(value = "카카오 액세스 토큰 (accessToken)", required = true) @RequestParam String accessToken) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        User user = userService.findByUserIdAndProvider(String.valueOf(profile.getKakao_account().getEmail()), provider).orElseThrow(AuthFailException::new);
        return new ResponseEntity<>(
                responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserNo()), user.getRoles())),
                HttpStatus.OK
        );
    }
}

