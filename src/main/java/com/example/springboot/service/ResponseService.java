package com.example.springboot.service;

import com.example.springboot.model.response.CommonResult;
import com.example.springboot.model.response.ListResult;
import com.example.springboot.model.response.SingleResult;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Getter
    public enum CommonResponse {
        SUCCESS(0, "성공하였습니다."),
        FAIL(-1, "실패하였습니다.");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Getter
    public enum SignInFailedResponse {
        SUCCESS(0, "로그인 성공했습니다."),
        FAIL(-1, "로그인 실패 했습니다.");

        int code;
        String msg;

        SignInFailedResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Getter
    public enum FindUserFailResponse {
        SUCCESS(0, "회원 조회 성공했습니다."),
        FAIL(-1, "회원 조회 실패했습니다.");

        int code;
        String msg;

        FindUserFailResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    public <T> SingleResult<T> getSingleResult(T data) {
        // Result가 상속 중인 CommonResult msg, code 전부 result 객체 담음
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
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
        return result;
    }

    // 회원 조회 실패 에러
    public CommonResult getFindUserFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(FindUserFailResponse.FAIL.getCode());
        result.setMsg(FindUserFailResponse.FAIL.getMsg());
        return result;
    }

    // 로그인 실패 에러
    public CommonResult getSignInFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(SignInFailedResponse.FAIL.getCode());
        result.setMsg(SignInFailedResponse.FAIL.getMsg());
        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
