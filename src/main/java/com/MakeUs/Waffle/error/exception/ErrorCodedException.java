package com.MakeUs.Waffle.error.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ErrorCodedException extends RuntimeException {
    protected final HttpStatus httpStatus;
    protected final String errorCode;

    protected ErrorCodedException(HttpStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = status;
        this.errorCode = errorCode.getCode();
    }

    @Override
    public String toString() {
        return "ErrorCodedException{" +
                "httpStatus=" + httpStatus +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}