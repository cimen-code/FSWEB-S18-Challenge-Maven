package com.workintech.fswebs18challengemaven.exceptions;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardException.class)
    public ResponseEntity<CardErrorResponse> handleCardException(
            CardException exception) {

        log.warn("Card API error: {}", exception.getMessage());

        return ResponseEntity
            .status(exception.getHttpStatus())
            .body(new CardErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CardErrorResponse> handleException(
            Exception exception) {

        log.error("Unexpected Card API error", exception);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new CardErrorResponse(exception.getMessage()));
    }
}
