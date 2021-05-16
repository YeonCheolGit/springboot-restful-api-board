package com.example.springboot.service.exception;

import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.model.response.SingleResult;
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

    // 기본 에러 발생
    public CommonResult getDefaultFailResult() {
        log.debug("getDefaultFailResult()");
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.DEFAULT_FAIL.isCheck());
        result.setCode(ErrorEnum.DEFAULT_FAIL.getCode());
        result.setMsg(ErrorEnum.DEFAULT_FAIL.getMsg());
        return result;
    }

    // 기본 성공 리턴
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(ErrorEnum.DEFAULT_SUCCESS.isCheck());
        result.setCode(ErrorEnum.DEFAULT_SUCCESS.getCode());
        result.setMsg(ErrorEnum.DEFAULT_SUCCESS.getMsg());
    }

    // 회원 조회 실패 에러
    public CommonResult getUserNotFoundResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.FIND_USER_FAIL.isCheck());
        result.setCode(ErrorEnum.FIND_USER_FAIL.getCode());
        result.setMsg(ErrorEnum.FIND_USER_FAIL.getMsg());
        return result;
    }

    // 로그인 실패 에러
    public CommonResult getEmailSignInFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.EMAIL_SIGNIN_FAIL.isCheck());
        result.setCode(ErrorEnum.EMAIL_SIGNIN_FAIL.getCode());
        result.setMsg(ErrorEnum.EMAIL_SIGNIN_FAIL.getMsg());
        return result;
    }

    // 카카오 API 연결 에러
    public CommonResult getKakaoApiFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.KAKAO_API_FAIL.isCheck());
        result.setCode(ErrorEnum.KAKAO_API_FAIL.getCode());
        result.setMsg(ErrorEnum.KAKAO_API_FAIL.getMsg());
        return result;
    }

    // 이미 가입 된 회원 에러
    public CommonResult getDuplicatedUserResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.DUPLICATED_USER_FAIL.isCheck());
        result.setCode(ErrorEnum.DUPLICATED_USER_FAIL.getCode());
        result.setMsg(ErrorEnum.DUPLICATED_USER_FAIL.getMsg());
        return result;
    }

    // 회원 인증 에러
    public CommonResult getAuthFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(ErrorEnum.AUTH_FAIL.isCheck());
        result.setCode(ErrorEnum.AUTH_FAIL.getCode());
        result.setMsg(ErrorEnum.AUTH_FAIL.getMsg());
        return result;
    }

}
