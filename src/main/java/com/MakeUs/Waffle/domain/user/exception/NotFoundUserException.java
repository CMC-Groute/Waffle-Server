package com.MakeUs.Waffle.domain.user.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotFoundException;

public class NotFoundUserException extends NotFoundException {

    public NotFoundUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
