package com.example.springboot.advice.exception;

public class SuccessCreated extends RuntimeException {

    public SuccessCreated(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public SuccessCreated(String msg) {
        super(msg);
    }

    public SuccessCreated() {
        super();
    }
}
