package com.MakeUs.Waffle.error.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    //COMMON
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}