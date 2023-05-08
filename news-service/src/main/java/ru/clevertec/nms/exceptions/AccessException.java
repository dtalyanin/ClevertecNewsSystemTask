package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class AccessException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public AccessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
