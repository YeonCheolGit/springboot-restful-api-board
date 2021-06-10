package com.example.springboot.service.exception;

import com.example.springboot.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResponseService {
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 모든 생성 성공에 대한 응답 입니다.
    public CommonResult getSuccessCreated() {
        log.debug("getSuccessCreated()");
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.SUCCESS_CREATED.isCheck());
        result.setCode(ErrorEnum.SUCCESS_CREATED.getCode());
        result.setMsg(ErrorEnum.SUCCESS_CREATED.getMsg());
        result.setStatus(ErrorEnum.SUCCESS_CREATED.getStatus());
        return result;
    }

    // 모든 삭제 성공에 대한 응답 입니다.
    public CommonResult getSuccessDeleted() {
        log.debug("getSuccessDeleted()");
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.SUCCESS_DELETED.isCheck());
        result.setCode(ErrorEnum.SUCCESS_DELETED.getCode());
        result.setMsg(ErrorEnum.SUCCESS_DELETED.getMsg());
        result.setStatus(ErrorEnum.SUCCESS_DELETED.getStatus());
        return result;
    }

    // 모든 조회 실패에 대한 응답 입니다.
    public CommonResult getFindAnyFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.FIND_ANY_FAIL.isCheck());
        result.setCode(ErrorEnum.FIND_ANY_FAIL.getCode());
        result.setMsg(ErrorEnum.FIND_ANY_FAIL.getMsg());
        result.setStatus(ErrorEnum.FIND_ANY_FAIL.getStatus());
        return result;
    }

    // 로그인 실패에 대한 응답 입니다.
    public CommonResult getEmailSignInFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.EMAIL_SIGNIN_FAIL.isCheck());
        result.setCode(ErrorEnum.EMAIL_SIGNIN_FAIL.getCode());
        result.setMsg(ErrorEnum.EMAIL_SIGNIN_FAIL.getMsg());
        result.setStatus(ErrorEnum.EMAIL_SIGNIN_FAIL.getStatus());
        return result;
    }

    // 카카오 API 호출 실패에 대한 응답 입니다.
    public CommonResult getKakaoApiFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.KAKAO_API_FAIL.isCheck());
        result.setCode(ErrorEnum.KAKAO_API_FAIL.getCode());
        result.setMsg(ErrorEnum.KAKAO_API_FAIL.getMsg());
        result.setStatus(ErrorEnum.KAKAO_API_FAIL.getStatus());
        return result;
    }

    // 모든 중복 데이터 에러에 대한 응답 입니다.
    public CommonResult getDuplicatedUserResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.DUPLICATED_USER_FAIL.isCheck());
        result.setCode(ErrorEnum.DUPLICATED_USER_FAIL.getCode());
        result.setMsg(ErrorEnum.DUPLICATED_USER_FAIL.getMsg());
        result.setStatus(ErrorEnum.DUPLICATED_USER_FAIL.getStatus());
        return result;
    }

    // 회원 인증 실패에 대한 응답 입니다. (Token 시간 만료, 미 로그인 등)
    public CommonResult getAuthFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.AUTH_FAIL.isCheck());
        result.setCode(ErrorEnum.AUTH_FAIL.getCode());
        result.setMsg(ErrorEnum.AUTH_FAIL.getMsg());
        result.setStatus(ErrorEnum.AUTH_FAIL.getStatus());
        return result;
    }

    // 바인딩 에러에 대한 응답 입니다.
    public CommonResult getBindFailResult() {
        log.debug("getBindFailResult()");
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.BIND_FAIL.isCheck());
        result.setCode(ErrorEnum.BIND_FAIL.getCode());
        result.setMsg(ErrorEnum.BIND_FAIL.getMsg());
        result.setStatus(ErrorEnum.BIND_FAIL.getStatus());
        return result;
    }

    // 유효성 검증 실패에 대한 응답 입니다.
    public CommonResult getMethodArgumentNotValidResult() {
        log.debug("getMethodArgumentNotValidResult()");
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.VALID_FAIL.isCheck());
        result.setCode(ErrorEnum.VALID_FAIL.getCode());
        result.setMsg(ErrorEnum.VALID_FAIL.getMsg());
        result.setStatus(ErrorEnum.VALID_FAIL.getStatus());
        return result;
    }

    // 기본 에러 발생
    public CommonResult getDefaultFailResult() {
        log.debug("getDefaultFailResult()");
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.DEFAULT_FAIL.isCheck());
        result.setCode(ErrorEnum.DEFAULT_FAIL.getCode());
        result.setMsg(ErrorEnum.DEFAULT_FAIL.getMsg());
        result.setStatus(ErrorEnum.DEFAULT_FAIL.getStatus());
        return result;
    }

    // 기본 성공 리턴
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(ErrorEnum.DEFAULT_SUCCESS.isCheck());
        result.setCode(ErrorEnum.DEFAULT_SUCCESS.getCode());
        result.setMsg(ErrorEnum.DEFAULT_SUCCESS.getMsg());
        result.setStatus(ErrorEnum.DEFAULT_SUCCESS.getStatus());
    }
}
