package com.MakeUs.Waffle.domain.user.exception;


import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.BusinessException;

public class NotValidPasswordException extends BusinessException {

    public NotValidPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
