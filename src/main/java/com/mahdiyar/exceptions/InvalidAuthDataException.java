package com.mahdiyar.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author mahdiyar
 */
public class InvalidAuthDataException extends ServiceException {
    @Override
    HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    String message() {
        return "Invalid Authentication.";
    }
}
