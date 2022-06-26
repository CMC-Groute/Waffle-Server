package com.MakeUs.Waffle.error.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicatedResourceException extends ErrorCodedException {

    public DuplicatedResourceException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}