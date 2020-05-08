package com.mahdiyar.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author mahdiyar
 */
@AllArgsConstructor
@Data
public class ErrorResponse<T> {
    private int code;
    private String message;
    private T content;
}
