package com.MakeUs.Waffle.error.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotMatchResourceException extends ErrorCodedException {

    public NotMatchResourceException(ErrorCode errorCode) {
        super(HttpStatus.FORBIDDEN, errorCode);
    }

}