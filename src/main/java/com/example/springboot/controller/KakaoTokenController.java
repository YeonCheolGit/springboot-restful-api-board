package com.example.springboot.controller;

import com.example.springboot.service.social.KakaoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = {"4. Social"})
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/social/login")
public class KakaoTokenController {

    private final Environment environment;
    private final KakaoService kakaoService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    // 카카오 로그인 팝업 API 연결 주소
    @GetMapping
    public ModelAndView kakaoLogin(ModelAndView mv) {
        StringBuilder loginUrl = new StringBuilder()
                .append(environment.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect)
                .append("&response_type=code");
        mv.addObject("loginUrl", loginUrl);
        mv.setViewName("login");
        return mv;
    }

    // 카카오 로그인 정보 제공 동의 후 정보
    @GetMapping(value = "/kakao")
    public ModelAndView redirectKakao(ModelAndView mv, @RequestParam String code) {
        mv.addObject("authinfo", kakaoService.getKakaoTokenInfo(code));
        mv.setViewName("redirectKakao");
        return mv;
    }
}
