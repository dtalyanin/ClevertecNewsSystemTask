package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class AccessException extends IllegalArgumentException {
    private final Object invalidObject;
    private final ErrorCode errorCode;

    public AccessException(String message, Object invalidObject, ErrorCode errorCode) {
        super(message);
        this.invalidObject = invalidObject;
        this.errorCode = errorCode;
    }
}
