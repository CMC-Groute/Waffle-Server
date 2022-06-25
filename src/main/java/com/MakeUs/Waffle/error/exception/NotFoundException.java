package com.MakeUs.Waffle.error.exception;

import com.MakeUs.Waffle.error.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}