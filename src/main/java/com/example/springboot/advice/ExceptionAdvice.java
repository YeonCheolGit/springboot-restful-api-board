package com.example.springboot.advice;

import com.example.springboot.advice.exception.EmailSignInFailException;
import com.example.springboot.advice.exception.UserNotFoundException;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        System.out.println(e.getMessage());
        return responseService.getDefaultFailResult();
    }

    // 회원 조회 에러 발생
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        System.out.println(e.getMessage());
        return responseService.getFindUserFailResult();
    }

    @ExceptionHandler(EmailSignInFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailNotFoundException(HttpServletRequest request, EmailSignInFailException e) {
        System.out.println(e.getMessage());
        return responseService.getSignInFailResult();
    }
}
