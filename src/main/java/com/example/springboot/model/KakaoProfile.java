package com.example.springboot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
// 카카오 Access Token으로 정보 가지고 올 경우 제공 정보 입니다
public class KakaoProfile {

    private long userId;
    private Properties properties;

    @Getter @Setter
    @ToString
    private static class Properties {
        private String nickName;
        private String thumbnail_image;
        private String profile_image;
    }
}
