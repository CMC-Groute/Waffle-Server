package com.MakeUs.Waffle.error;

import com.MakeUs.Waffle.error.exception.ErrorMessage;

public class NotFoundException extends RuntimeException{

    private ErrorMessage errorMessage;

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}