package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends IllegalArgumentException {
    private final String username;
    private final ErrorCode errorCode;

    public UserNotFoundException(String s, String username, ErrorCode errorCode) {
        super(s);
        this.username = username;
        this.errorCode = errorCode;
    }
}
