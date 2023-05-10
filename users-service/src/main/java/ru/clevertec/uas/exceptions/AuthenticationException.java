package ru.clevertec.uas.exceptions;

import lombok.Getter;

@Getter
public class AuthenticationException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public AuthenticationException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }
}
