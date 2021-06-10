package com.example.springboot.controller;

import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.exception.ResponseService;
import com.example.springboot.service.social.KakaoService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = {"4. Social"})
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/social/login")
public class KakaoTokenController {

    private final Environment environment;
    private final KakaoService kakaoService;
    private final ResponseService responseService;
    private final RestTemplate restTemplate;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    // 카카오 로그인 팝업 API 연결 버튼 누르면 연결
//    @GetMapping
//    public ModelAndView kakaoLogin(ModelAndView mv) {
//        StringBuilder loginUrl = new StringBuilder()
//                .append(environment.getProperty("spring.social.kakao.url.login"))
//                .append("?client_id=").append(kakaoClientId)
//                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect)
//                .append("&response_type=code");
//        mv.addObject("loginUrl", loginUrl);
//        mv.setViewName("login");
//        return mv;
//    }

    /*
     1. 프론트에서 카카오 회원가입 버튼 클릭 합니다.
     2. 카카오 인가 코드 발급 위한 URI 생성 후 '/api/v1/signUp/kakao_auth_code/return' 파라미터로 넘깁니다.
     */
    @GetMapping(value = "/kakaoAuthCode")
    @ApiOperation(value = "카카오 인가 코드 발급 URI")
    public ResponseEntity<SingleResult> kakaoLogin() {
        StringBuilder loginUrl = new StringBuilder()
                .append(environment.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect)
                .append("&response_type=code");
        System.out.println(loginUrl);
        return new ResponseEntity<>(responseService.getSingleResult(loginUrl), HttpStatus.OK);
    }

    /*
     1. kakaoLogin에서 받은 인가 코드 파라미터로 받습니다.
     2. 받은 인가 코드 '/api/v1/signUp/kakao_auth_code'로 넘깁니다. (회원가입 PostM method 위함)
     *  프론트를 거치지 않습니다.
     */
    @ApiOperation(value = "카카오 계정 가입", notes = "회원가입 시 post 메서드 위해 해당 컨트롤러 거칩니다.")
    @GetMapping(value = "/kakao_auth_code/return")
    public ResponseEntity<String> authCodeReturn(@ApiParam(value = "카카오 토큰 (access_token)", required = true) @RequestParam String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);

        final String url = "http://localhost:8080/api/v1/signUp/kakao_auth_code";
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response;
    }

    // 카카오 로그인 정보 제공 동의 후 정보
    @GetMapping(value = "/kakao")
    public ModelAndView redirectKakao(ModelAndView mv, @RequestParam String code) {
        mv.addObject("authinfo", kakaoService.getKakaoTokenInfo(code));
        mv.setViewName("redirectKakao");
        return mv;
    }
}
