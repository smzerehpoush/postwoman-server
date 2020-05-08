package com.mahdiyar.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author mahdiyar
 */
@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse<Object>> handleServiceException(ServiceException exception) {
        return ResponseEntity.status(exception.status()).body(
                new ErrorResponse<>(exception.status().value(), exception.message(), exception.content())
        );
    }
}
