package com.MakeUs.Waffle.error;

import com.MakeUs.Waffle.error.exception.ErrorCodedException;
import com.MakeUs.Waffle.error.exception.OpenApiException;
import com.MakeUs.Waffle.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorCodedException.class)
    public ResponseEntity<ErrorResponse> handleErrorCodedException(ErrorCodedException ex) {
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ErrorResponse.of(ex));
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponse> mailSendFailedExceptionHandler(MailException ex) {
        log.error("MailException: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.MAIL_SEND_FAILED;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        errorCode.getCode(),
                        errorCode.getMessage()
                ));
    }

    @ExceptionHandler(OpenApiException.class)
    public ResponseEntity<ErrorResponse> openApiServerExceptionHandler(OpenApiException ex) {
        log.error("OpenApiException: {}", ex.getMessage());
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ErrorResponse.of(ex));
    }
}