package com.example.springboot.service.exception;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    DEFAULT_SUCCESS(true, 0, "성공하였습니다."),
    DEFAULT_FAIL(false, -1, "실패했습니다."),
    EMAIL_SIGNIN_FAIL(false, -1, "입력된 아이디(이메일) 혹은 비밀번호가 없습니다."),
    FIND_USER_FAIL(false, -1, "회원 조회 실패했습니다."),
    KAKAO_API_FAIL(false, -1, "카카오 API 통신 실패"),
    DUPLICATED_USER_FAIL(false,-1, "이미 가입된 회원 입니다"),
    AUTH_FAIL(false,-1, "회원 인증 오류 입니다");


    private boolean check;
    private int code;
    private String msg;

    ErrorEnum(boolean check, int code, String msg) {
        this.check = check;
        this.code = code;
        this.msg = msg;
    }
}
