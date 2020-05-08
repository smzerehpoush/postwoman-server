package com.mahdiyar.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author mahdiyar
 */
public class TokenNotFoundException extends ServiceException {
    @Override
    HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    String message() {
        return "Token Not Found";
    }
}
