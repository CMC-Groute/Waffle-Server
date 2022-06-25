package com.MakeUs.Waffle.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_FOUND_RESOURCE_ERROR(
            "존재하지 않는 리소스입니다.",
            HttpStatus.BAD_REQUEST.value()
    ),
    INTERNAL_SERVER_ERROR(
            "서버 내부 에러입니다.",
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    ),
    INVALID_INPUT_ERROR(
            "적절한 입력값이 아닙니다.",
            HttpStatus.BAD_REQUEST.value()
    ),
    CONFLICT_VALUE_ERROR(
            "중복된 값입니다",
            HttpStatus.CONFLICT.value()
    ),
    CONFLICT_PASSWORD_ERROR(
            "잘못된 비밀번호입니다.",
            HttpStatus.CONFLICT.value()
    ),
    INVALID_EMAIL_ERROR(
            "유효하지 않은 이메일코드 입니다.",
            HttpStatus.BAD_REQUEST.value()
    ),

    NOT_FOUND_EMAIL_CODE(
            "잘못된 이메일 인증코드 입니다.",
            HttpStatus.CONFLICT.value()
    );


    private final String message;
    private final int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
