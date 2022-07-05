package com.MakeUs.Waffle.response;

import com.MakeUs.Waffle.error.exception.ErrorCodedException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private int status;
    private String data;

    public ErrorResponse(int status, String data) {
        this.status = status;
        this.data = data;
    }

    public static ErrorResponse of(ErrorCodedException ex) {
        return new ErrorResponse(
                ex.getHttpStatus()
                        .value(),
                ex.getMessage()
        );
    }
}