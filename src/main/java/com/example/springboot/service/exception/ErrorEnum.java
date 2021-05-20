package com.example.springboot.service.exception;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    // 성공 응답
    DEFAULT_SUCCESS(true, 0, "성공했습니다.", 200),
    SUCCESS_CREATED(true, 0, "생성에 성공했습니다.", 201),
    SUCCESS_DELETED(true, 0, "삭제에 성공했습니다.", 204),

    // 실패 응답
    DEFAULT_FAIL(false, -1, "실패했습니다.", 500),
    EMAIL_SIGNIN_FAIL(false, -1, "입력된 아이디(이메일) 혹은 비밀번호가 일치하지 않습니다.", 403),
    FIND_ANY_FAIL(false, -1, "해당 조회 내용을 찾을 수 없습니다", 404),
    KAKAO_API_FAIL(false, -1, "카카오 API 통신 실패", 503),
    DUPLICATED_USER_FAIL(false,-1, "이미 가입된 회원 입니다", 409),
    AUTH_FAIL(false,-1, "회원 인증 오류 입니다", 403),
    BIND_FAIL(false,-1, "데이터 바인딩 오류 입니다.", 400);

    private final boolean check;
    private final int code;
    private final String msg;
    private final int status;

    ErrorEnum(boolean check, int code, String msg, int status) {
        this.check = check;
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}
