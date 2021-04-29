package com.example.springboot.advice.exception;

/*
 * 로그인 실패 시 발생 에러
 */
public class EmailSignInFailException extends RuntimeException {

    public EmailSignInFailException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public EmailSignInFailException(String msg) {
        super(msg);
    }

    public EmailSignInFailException() {
        super();
    }
}
