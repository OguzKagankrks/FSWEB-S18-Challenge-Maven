package com.workintech.fswebs18challengemaven.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardException.class)
    public ResponseEntity<CardErrorResponse> exceptionHandler(CardException cardException) {
        log.error("CardException: {}", cardException.getMessage(), cardException);
        CardErrorResponse errorResponse = new CardErrorResponse(cardException.getMessage());
        return new ResponseEntity<>(errorResponse, cardException.getHttpStatus());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CardErrorResponse> exceptionHandler(IllegalStateException ex) {
        log.error("IllegalStateException: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new CardErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CardErrorResponse> exceptionHandler(Exception exception) {
        log.error("Unhandled Exception: {}", exception.getMessage(), exception);
        CardErrorResponse errorResponse = new CardErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
