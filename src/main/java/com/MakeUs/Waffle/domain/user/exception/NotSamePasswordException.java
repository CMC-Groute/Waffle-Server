package com.MakeUs.Waffle.domain.user.exception;


import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.BusinessException;

public class NotSamePasswordException extends BusinessException {

    public NotSamePasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
