package ru.clevertec.nms.security.exceptions;

import ru.clevertec.nms.exceptions.ErrorCode;

public class UserNotFoundException extends IllegalArgumentException {
    private final String username;
    private final ErrorCode errorCode;

    public UserNotFoundException(String s, String username, ErrorCode errorCode) {
        super(s);
        this.username = username;
        this.errorCode = errorCode;
    }
}
