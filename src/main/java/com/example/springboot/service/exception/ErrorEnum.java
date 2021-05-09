package com.example.springboot.service.exception;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    SUCCESS(true, 0, "성공하였습니다."),

    EMAIL_FAIL(false, -1, "이메일 로그인 실패 했습니다222");


    private boolean check;
    private int code;
    private String msg;

    ErrorEnum(boolean check, int code, String msg) {
        this.check = check;
        this.code = code;
        this.msg = msg;
    }
}
