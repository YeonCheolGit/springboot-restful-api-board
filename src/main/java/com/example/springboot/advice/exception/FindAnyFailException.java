package com.example.springboot.advice.exception;

public class FindAnyFailException extends RuntimeException {

    public FindAnyFailException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public FindAnyFailException(String msg) {
        super(msg);
    }

    public FindAnyFailException() {
        super();
    }
}
