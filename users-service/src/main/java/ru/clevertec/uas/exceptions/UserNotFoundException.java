package ru.clevertec.uas.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends IllegalArgumentException {
    private final Object incorrectValue;
    private final ErrorCode errorCode;

    public UserNotFoundException(String s, Object incorrectValue, ErrorCode errorCode) {
        super(s);
        this.incorrectValue = incorrectValue;
        this.errorCode = errorCode;
    }
}
