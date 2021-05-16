package com.example.springboot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 카카오 Access Token으로 정보 가지고 올 경우 제공 정보 입니다
@Getter @Setter
@ToString
public class KakaoProfile {

    public Kakao_account kakao_account;

    @Getter @Setter
    @ToString
    public static class Kakao_account {
        private String email;
    }
}
