package com.MakeUs.Waffle.domain.user.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.BusinessException;

public class DuplicateUserException extends BusinessException {

    public DuplicateUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}