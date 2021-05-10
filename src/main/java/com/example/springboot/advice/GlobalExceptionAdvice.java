package com.example.springboot.advice;

import com.example.springboot.advice.exception.*;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.service.exception.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(Exception e) {
        log.error("defaultException : ", e);
        return responseService.getDefaultFailResult();
    }

    // 회원 조회 에러 발생 합니다
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(UserNotFoundException e) {
        log.error("userNotFoundException : ", e);
        return responseService.getUserNotFoundResult();
    }

    // 이메일 계정 로그인의 에러 발생 합니다
    @ExceptionHandler(EmailSignInFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailNotFoundException(EmailSignInFailException e) {
        log.error("emailNotFoundException : ", e);
        return responseService.getEmailSignInFailResult();
    }

    // 카카오 API 통신의 에러 발생 합니다
    @ExceptionHandler(KakaoApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult kakaoApiException(KakaoApiException e) {
        log.error("kakaoApiException : ", e);
        return responseService.getKakaoApiFailResult();
    }

    // 이미 가입 된 회원 에러 입니다
    @ExceptionHandler(DuplicatedUserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult duplicatedUserException(KakaoApiException e) {
        log.error("duplicatedUserException : ", e);
        return responseService.getDuplicatedUserResult();
    }

    // 회원 인증 오류 입니다
    @ExceptionHandler(AuthFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authFailException(AuthFailException e) {
        log.error("authFailException : ", e);
        return responseService.getAuthFailResult();
    }
}
