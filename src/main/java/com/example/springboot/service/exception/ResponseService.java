package com.example.springboot.service.exception;

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
    public enum EmailSignInFailedResponse {
        SUCCESS(0, "이메일 로그인 성공 했습니다."),
        FAIL(-1, "이메일 로그인 실패 했습니다.");

        int code;
        String msg;

        EmailSignInFailedResponse(int code, String msg) {
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
    @Getter
    public enum KakaoApiFailResponse {
        SUCCESS(0, "카카오 API 통신 성공"),
        FAIL(-1, "카카오 API 통신 실패");

        int code;
        String msg;

        KakaoApiFailResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
    @Getter
    public enum DuplicatedUserResponse {
        SUCCESS(0, "가입되지 않은 회원 입니다"),
        FAIL(-1, "이미 가입된 회원 입니다");

        int code;
        String msg;

        DuplicatedUserResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
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
    public CommonResult getEmailSignInFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(EmailSignInFailedResponse.FAIL.getCode());
        result.setMsg(EmailSignInFailedResponse.FAIL.getMsg());
        return result;
    }

    // 카카오 API 연결 에러 입니다
    public CommonResult getKakaoApiFailResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(KakaoApiFailResponse.FAIL.getCode());
        result.setMsg(KakaoApiFailResponse.FAIL.getMsg());
        return result;
    }

    // 이미 가입 된 회원 에러 입니다
    public CommonResult getDuplicatedUserResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(DuplicatedUserResponse.FAIL.getCode());
        result.setMsg(DuplicatedUserResponse.FAIL.getMsg());
        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
