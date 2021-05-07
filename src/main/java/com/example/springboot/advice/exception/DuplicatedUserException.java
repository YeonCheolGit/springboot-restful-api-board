package com.example.springboot.advice.exception;

// 중복 회원 에러 입니다
public class DuplicatedUserException extends RuntimeException {

    public DuplicatedUserException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public DuplicatedUserException(String msg) {
        super(msg);
    }

    public DuplicatedUserException() {
        super();
    }
}
