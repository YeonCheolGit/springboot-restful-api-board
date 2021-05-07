package com.example.springboot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 카카오 계정 정보 동의 시 제공 정보 입니다
public class KakaoAuth {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
