package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

@Getter
public class AccessException extends IllegalArgumentException {

    private final ErrorCode errorCode;

    public AccessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
