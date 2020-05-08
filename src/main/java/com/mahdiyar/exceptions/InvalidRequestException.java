package com.mahdiyar.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author mahdiyar
 */
public class InvalidRequestException extends ServiceException {
    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    String message() {
        return "Invalid Request.";
    }
}
