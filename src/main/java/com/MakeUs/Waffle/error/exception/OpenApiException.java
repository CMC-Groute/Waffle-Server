package com.MakeUs.Waffle.error.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OpenApiException extends ErrorCodedException {

    public OpenApiException(ErrorCode errorCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }
}