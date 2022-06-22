package com.MakeUs.Waffle.domain.place.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.BusinessException;

public class WrongUserException extends BusinessException {
    public WrongUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
