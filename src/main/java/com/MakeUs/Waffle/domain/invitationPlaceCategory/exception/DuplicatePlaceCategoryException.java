package com.MakeUs.Waffle.domain.invitationPlaceCategory.exception;

import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.BusinessException;

public class DuplicatePlaceCategoryException extends BusinessException {

    public DuplicatePlaceCategoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
