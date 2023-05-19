package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

/**
 *  Exception for missing user's permissions for executing operation
 */
@Getter
public class AccessException extends IllegalArgumentException {

    private final ErrorCode errorCode;

    public AccessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
