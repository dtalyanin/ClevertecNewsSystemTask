package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends IllegalArgumentException {
    private final long incorrectId;
    private final ErrorCode errorCode;

    public NotFoundException(String message, long incorrectId, ErrorCode errorCode) {
        super(message);
        this.incorrectId = incorrectId;
        this.errorCode = errorCode;
    }
}
