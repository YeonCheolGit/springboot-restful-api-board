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
//    @Getter
//    public enum CommonResponse {
//        SUCCESS(0, "성공하였습니다."),
//        FAIL(-1, "실패하였습니다.");
//
//        int code;
//        String msg;
//
//        CommonResponse(int code, String msg) {
//            this.code = code;
//            this.msg = msg;
//        }
//    }
//    @Getter
//    public enum EmailSignInFailedResponse {
//        FAIL(-1, "이메일 로그인 실패 했습니다.");
//
//        int code;
//        String msg;
//
//        EmailSignInFailedResponse(int code, String msg) {
//            this.code = code;
//            this.msg = msg;
//        }
//    }
//    @Getter
//    public enum FindUserFailResponse {
//        FAIL(-1, "회원 조회 실패했습니다.");
//
//        int code;
//        String msg;
//
//        FindUserFailResponse(int code, String msg) {
//            this.code = code;
//            this.msg = msg;
//        }
//    }
//    @Getter
//    public enum KakaoApiFailResponse {
//        FAIL(-1, "카카오 API 통신 실패");
//
//        int code;
//        String msg;
//
//        KakaoApiFailResponse(int code, String msg) {
//            this.code = code;
//            this.msg = msg;
//        }
//    }
//    @Getter
//    public enum DuplicatedUserResponse {
//        FAIL(-1, "이미 가입된 회원 입니다");
//
//        int code;
//        String msg;
//
//        DuplicatedUserResponse(int code, String msg) {
//            this.code = code;
//            this.msg = msg;
//        }
//    }

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
