package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends IllegalArgumentException {
    private final long incorrectId;
    private final ErrorCode errorCode;

    public NotFoundException(String s, long incorrectId, ErrorCode errorCode) {
        super(s);
        this.incorrectId = incorrectId;
        this.errorCode = errorCode;
    }
}
