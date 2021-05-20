package com.example.springboot.advice.exception;

public class SuccessDeleted extends RuntimeException {

    public SuccessDeleted(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public SuccessDeleted(String msg) {
        super(msg);
    }

    public SuccessDeleted() {
        super();
    }
}
