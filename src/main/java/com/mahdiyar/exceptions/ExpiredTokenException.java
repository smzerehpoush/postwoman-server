package com.mahdiyar.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author mahdiyar
 */
public class ExpiredTokenException extends ServiceException {
    private final String token;

    public ExpiredTokenException(String token) {
        this.token = token;
    }

    @Override
    HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    String message() {
        return "Token Is Expired : [" + this.token + "]";
    }
}
