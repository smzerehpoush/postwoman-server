package com.mahdiyar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ServiceException extends Exception {
    HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    Object content() {
        return null;
    }

    abstract String message();
}
