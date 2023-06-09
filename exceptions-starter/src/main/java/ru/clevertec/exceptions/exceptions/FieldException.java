package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

/**
 * Getting object field with reflection exception
 */
@Getter
public class FieldException extends RuntimeException {

    private final ErrorCode errorCode;

    public FieldException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
