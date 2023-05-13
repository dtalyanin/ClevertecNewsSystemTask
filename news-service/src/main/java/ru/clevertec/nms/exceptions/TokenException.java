package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class TokenException extends IllegalArgumentException {

    private final ErrorCode errorCode;

    public TokenException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }
}
