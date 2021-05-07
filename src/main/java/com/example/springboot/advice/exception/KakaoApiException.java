package com.example.springboot.advice.exception;

public class KakaoApiException extends RuntimeException {
    public KakaoApiException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public KakaoApiException(String msg) {
        super(msg);
    }

    public KakaoApiException() {
        super();
    }
}
