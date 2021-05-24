package com.example.springboot.advice;

import com.example.springboot.advice.exception.*;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.service.exception.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(Exception e) {
        log.info("defaultException : ", e);
        return responseService.getDefaultFailResult();
    }

    // 모든 생성 성공에 대한 응답입니다.
    @ExceptionHandler(SuccessCreated.class)
    @ResponseStatus(HttpStatus.CREATED)
    protected CommonResult successCreated(SuccessCreated e) {
        log.info("SuccessCreated : ", e);
        return responseService.getSuccessCreated();
    }

    // 모든 삭제 성공에 대한 응답입니다.
    @ExceptionHandler(SuccessDeleted.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected CommonResult successDeleted(SuccessDeleted e) {
        log.info("SuccessDeleted : ", e);
        return responseService.getSuccessCreated();
    }

    // 없는 데이터 조회 경우 발생합니다.
    @ExceptionHandler(FindAnyFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult findAnyFailException(FindAnyFailException e) {
        log.info("FindAnyFailException : ", e);
        return responseService.getFindAnyFailResult();
    }

    // 이메일 계정 로그인의 에러 발생 합니다
    @ExceptionHandler(EmailSignInFailException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult emailSignInFailException(EmailSignInFailException e) {
        log.info("EmailSignInFailException : ", e);
        return responseService.getEmailSignInFailResult();
    }

    // 카카오 API 통신의 에러 발생 합니다
    @ExceptionHandler(KakaoApiException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected CommonResult kakaoApiException(KakaoApiException e) {
        log.info("KakaoApiException : ", e);
        return responseService.getKakaoApiFailResult();
    }

    // 중복된 데이터에 대한 에러 발생 합니다.
    @ExceptionHandler(DuplicatedDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResult duplicatedDataException(DuplicatedDataException e) {
        log.info("DuplicatedDataException : ", e);
        return responseService.getDuplicatedUserResult();
    }

    // 인증에 대한 에러 발생 합니다.
    @ExceptionHandler(AuthFailException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult authFailException(AuthFailException e) {
        log.info("AuthFailException : ", e);
        return responseService.getAuthFailResult();
    }

    // 모든 바인딩 에러 발생 합니다.
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult bindException(BindException e) {
        log.info("BindException : ", e);
        return responseService.getBindFailResult();
    }

    // @Valid 유효성 검증 에러 발생 합니다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("BindException : ", e);
        return responseService.getBindFailResult();
    }
}
