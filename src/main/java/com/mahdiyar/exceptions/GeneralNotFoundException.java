package com.mahdiyar.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class GeneralNotFoundException extends ServiceException {
    private final String item;
    private final String field;
    private final String value;

    public GeneralNotFoundException(String item, String field, String value) {
        super();
        this.item = item;
        this.field = field;
        this.value = value;
    }

    @Override
    HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    String message() {
        return String.format("item [%s] with [%s]:[%s] not found.", item, field, value);
    }
}
