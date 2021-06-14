package com.example.springboot.controller;

import com.example.springboot.model.response.SingleResult;
import com.example.springboot.service.exception.ResponseService;
import com.example.springboot.service.social.KakaoService;
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

@Api(tags = {"4. Social"})
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/social/login")
public class KakaoTokenController {

    private final Environment environment;
    private final KakaoService kakaoService;
    private final ResponseService responseService;
    private final RestTemplate restTemplate;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    /*
     1. 프론트에서 카카오 회원가입 버튼 클릭 합니다.
     2. 카카오 인가 코드 발급 위한 URI 생성 후 '/api/v1/signUp/kakao_auth_code/return' 파라미터로 넘깁니다.
     */
    @GetMapping(value = "/kakaoAuthCode")
    @ApiOperation(value = "카카오 인가 코드 발급 URI")
    public ResponseEntity<SingleResult> kakaoSignUpButton() {
        StringBuilder loginUrl = new StringBuilder()
                .append(environment.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect)
                .append("&response_type=code");
        System.out.println("인가 코드 발급 URI >>> " + loginUrl);
        return new ResponseEntity<>(responseService.getSingleResult(loginUrl), HttpStatus.OK);
    }

    /*
     1. '/kakaoAuthCode'에서 받은 인가 코드 파라미터로 받습니다.
     2. 받은 인가 코드 '/api/v1/signUp/kakaoAuthCode'로 넘깁니다. (회원가입 Post method 위함)
     *  프론트를 거치지 않습니다.
     */
    @ApiOperation(value = "카카오 계정 가입", notes = "회원가입 시 post 메서드 위해 해당 컨트롤러 거칩니다.")
    @GetMapping(value = "/kakaoAuthCode/return")
    public ResponseEntity<String> authCodeReturn(@ApiParam(value = "카카오 인가 코드 (authorization code)", required = true) @RequestParam String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);

        final String url = "http://localhost:8080/api/v1/signUp/kakaoAuthCode";
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response;
    }
}
