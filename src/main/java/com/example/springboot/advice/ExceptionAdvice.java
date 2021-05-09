package com.example.springboot.advice;

import com.example.springboot.advice.exception.DuplicatedUserException;
import com.example.springboot.advice.exception.EmailSignInFailException;
import com.example.springboot.advice.exception.KakaoApiException;
import com.example.springboot.advice.exception.UserNotFoundException;
import com.example.springboot.model.response.CommonResult;
import com.example.springboot.service.exception.ResponseService;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // 회원 조회 에러 발생 합니다
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        System.out.println("=================== userNotFoundException ===================");
        System.out.println(e.getMessage());
        return responseService.getFindUserFailResult();
    }

    // 이메일 계정 로그인의 에러 발생 합니다
    @ExceptionHandler(EmailSignInFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailNotFoundException(HttpServletRequest request, EmailSignInFailException e) {
        System.out.println("=================== emailNotFoundException ===================");
        System.out.println(e.getMessage());
        return responseService.getEmailSignInFailResult();
    }

    // 카카오 API 통신의 에러 발생 합니다
    @ExceptionHandler(KakaoApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult kakaoApiException(HttpServletRequest request, KakaoApiException e) {
        System.out.println("=================== KakaoApiException ===================");
        System.out.println(e.getMessage());
        return responseService.getKakaoApiFailResult();
    }

    // 이미 가입 된 회원 에러 입니다
    @ExceptionHandler(DuplicatedUserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult duplicatedUserException(HttpServletRequest request, KakaoApiException e) {
        System.out.println("=================== duplicatedUserException ===================");
        System.out.println(e.getMessage());
        return responseService.getDuplicatedUserResult();
    }
}
