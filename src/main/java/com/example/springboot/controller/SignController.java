package com.example.springboot.controller;

import com.example.springboot.DTO.user.KakaoUserRequestDTO;
import com.example.springboot.DTO.user.UserRequestDTO;
import com.example.springboot.advice.exception.EmailSignInFailException;
import com.example.springboot.config.security.JwtTokenProvider;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.model.KakaoProfile;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.service.exception.ResponseService;
import com.example.springboot.service.social.KakaoService;
import com.example.springboot.service.user.UserService;
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
import java.util.Optional;

// 회원 로그인, 가입 관련 컨트롤러
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/sign")
@Slf4j
public class SignController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    /*
     * 이메일, 비밀번호 활용한 일반적인 회원가입 합니다.
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

        if (!passwordEncoder.matches(userPwd, user.getPassword()))
            throw new EmailSignInFailException(userId + " > 입력된 비밀번호가 일치하지 않습니다.");

        String jwtToken = jwtTokenProvider.createToken(String.valueOf(user.getUserNo()), user.getRoles());

        return new ResponseEntity<>(responseService.getSingleResult(jwtToken), HttpStatus.OK);
    }

    /*
     1. 인가 코드 (authorization code) 발급 후 redirect uri 컨트롤러
     2. 인가 코드 파라미터 받은 후 getKakaoTokenInfo(인가 코드) 실행 --> access_token 발급 합니다.
     3. getKakaoProfile(access_token) --> 'https://kapi.kakao.com/v2/user/me' 사용자 프로필 정보 가지고 옵니다.
     4. 회원 가입 절차 진행 합니다.
     * 이미 가입된 회원인 경우 로그인 진행 합니다. (kakaoService.signInByKakaoAccessToken(accessToken)
     */
    @ApiOperation(value = "카카오 계정으로 회원가입/로그인",
            notes = "카카오 Access Token 이용 회원가입/로그인 합니다. 이미 가입된 회원일 경우, 로그인 메서드 실행 됩니다.")
    @PostMapping(value = "/kakaoAuthCode")
    public ResponseEntity<CommonResult> signUpOrInByKakaoAccessToken(@ApiParam(required = true) @RequestParam String code) {
        String accessToken = kakaoService.getKakaoTokenInfo(code).getAccess_token(); // 인가 코드를 바탕으로 access token 가지고 옵니다.

        Role role = new Role();
        role.setRoleNo(1);

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken); // 만들어진 access token으로 회원 정보 가지고 옵니다.
        Optional<User> user = userService.findByUserIdAndProvider(String.valueOf(profile.getKakao_account().getEmail()), "kakao");

        if (user.isPresent()) // 카카오 로그인 시 해당 핸들러 사용 합니다. 즉 같은 이메일이 있기 때문에 if문 실행 됩니다.
            return new ResponseEntity<>(responseService.getSingleResult(kakaoService.signInByKakaoAccessToken(accessToken)), HttpStatus.OK);

        userService.save(KakaoUserRequestDTO.builder()
                .userId(String.valueOf(profile.getKakao_account().getEmail()))
                .userName("kakao")
                .provider("kakao")
                .build()
        );

        return new ResponseEntity<>(responseService.getSuccessCreated(), HttpStatus.CREATED);
    }
}

