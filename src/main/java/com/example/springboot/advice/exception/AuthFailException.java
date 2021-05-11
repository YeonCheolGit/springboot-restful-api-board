package com.example.springboot.advice.exception;

// 회원 인증 오류 입니다
public class AuthFailException extends RuntimeException {

    public AuthFailException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public AuthFailException(String msg) {
        super(msg);
    }

    public AuthFailException() {
        super();
    }
}
