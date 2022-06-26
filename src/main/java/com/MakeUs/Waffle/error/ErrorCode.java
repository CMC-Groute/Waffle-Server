package com.MakeUs.Waffle.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("G001", "정의되지 않은 에러가 발생했습니다."),
    INVALID_INPUT_VALUE("G002", "올바른 입력 형식이 아닙니다."),
    NOT_FOUND_USER("G003", "존재하지 않는 사용자입니다."),
    NOT_MATCH_WRITER("G004", "게시글의 작성자가 아닙니다."),
    NOT_MATCH_COMMENT("G005", "게시글의 댓글이 아닙니다."),
    DUPLICATE_USER_ERROR("G006", "중복회원입니다."),
    NOT_SAME_PASSWORD("G007","올바르지 않은 비밀번호입니다."),
    NOT_MATCH_PASSWORD("G007","확인비밀번호가 일치하지 않습니다."),

    NOT_FOUND_PLACE("M001", "존재하지 않는 장소입니다."),

    NOT_MATCH_INVITATION_MEMBER("C002","초대되지 않은 회원입니다."),
    NOT_FOUND_INVITATION("C003","존재하지 않는 초대장입니다."),
    NOT_MATCH_INVITATION_CODE("C004","유효하지 않은 초대장 코드 입니다."),

    DUPLICATE_PLACE_CATEGORY("D001","중복된 카테고리 입니다."),

    NOT_FOUND_INVITATION_MEMBER("F001","약속 멤버가 없습니다."),

    NOT_MATCH_EMAIL_CODE("E001", "발송된 인증 코드가 아닙니다."),
    MAIL_SEND_FAILED("E002", "이메일 전송에 실패했습니다."),
    INVALID_EMAIL("E003", "이메일 또는 인증코드가 유효하지 않습니다."),

    OPENAPI_CONN_FAIL("O001", "공공 api 연결에 실패했습니다."),
    ALREADY_EXIST_OPENAPI_CONN("O002", "이미 공공 api 서버에 연결돼있습니다."),
    UNSUPPORTED_ENCODING("O003", "지원하지 않는 형식으로 인코딩돼있습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}