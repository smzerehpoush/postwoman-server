package com.mahdiyar.exceptions;

/**
 * @author mahdiyar
 */
public class GeneralDuplicateException extends ServiceException {
    private final String field;
    private final String value;

    public GeneralDuplicateException(String field, String value) {
        super();
        this.field = field;
        this.value = value;
    }

    @Override
    String message() {
        return String.format("[%s] with value [%s] is duplicated.", this.field, this.value);
    }
}
