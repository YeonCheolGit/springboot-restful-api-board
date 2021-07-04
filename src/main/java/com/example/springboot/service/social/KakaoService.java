package com.example.springboot.service.social;

import com.example.springboot.advice.exception.AuthFailException;
import com.example.springboot.advice.exception.KakaoApiException;
import com.example.springboot.config.security.JwtTokenProvider;
import com.example.springboot.entity.User;
import com.example.springboot.model.KakaoAuth;
import com.example.springboot.model.KakaoProfile;
import com.example.springboot.service.user.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class KakaoService {

    private final RestTemplate restTemplate;
    private final Environment environment;
    private final Gson gson;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    /*
     * 카카오 Acesss Token 파라미터로 회원 정보를 가지고 옵니다
     * 'https://kapi.kakao.com/v2/user/me' 사용자 프로필 정보 가지고 옵니다.
     */
    public KakaoProfile getKakaoProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(Objects.requireNonNull(environment.getProperty("spring.social.kakao.url.profile")), request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), KakaoProfile.class);
        } catch (Exception e) {
            throw new KakaoApiException("카카오 API 회원 정보 가지고 오는 것 에러 발생 했습니다.");
        }
        throw new KakaoApiException("카카오 API 회원 정보 가지고 오는 것 에러 발생 했습니다.");
    }

    /*
     * 카카오로 부터 발급 받은 인가 코드를 파라미터로 받아, access token 발급 합니다.
     */
    public KakaoAuth getKakaoTokenInfo(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirect);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(Objects.requireNonNull(environment.getProperty("spring.social.kakao.url.token")), request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), KakaoAuth.class);
        }
        return null;
    }

    // 카카오 로그인 시 사용 됩니다.
    public String signInByKakaoAccessToken(String accessToken) {
        KakaoProfile profile = getKakaoProfile(accessToken); // accessToken으로 회원 정보 추출 합니다.
        User user = userService.findByUserIdAndProvider(String.valueOf(profile.getKakao_account().getEmail()), "kakao").orElseThrow(AuthFailException::new); // 추출한 회원정보와 DB를 조회 합니다.
        return jwtTokenProvider.createToken(String.valueOf(user.getUserNo()), user.getRoles()); // 생성된 JWT 토큰을 return 합니다.
    }
}
