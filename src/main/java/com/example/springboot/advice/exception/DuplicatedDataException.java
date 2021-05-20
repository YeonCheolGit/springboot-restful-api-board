package com.example.springboot.advice.exception;

// 중복 회원 에러 입니다
public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public DuplicatedDataException(String msg) {
        super(msg);
    }

    public DuplicatedDataException() {
        super();
    }
}
