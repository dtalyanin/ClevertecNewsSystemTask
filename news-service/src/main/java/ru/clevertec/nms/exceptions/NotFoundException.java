package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends IllegalArgumentException {
    private final Object incorrectValue;
    private final ErrorCode errorCode;

    public NotFoundException(String message, Object incorrectValue, ErrorCode errorCode) {
        super(message);
        this.incorrectValue = incorrectValue;
        this.errorCode = errorCode;
    }
}
