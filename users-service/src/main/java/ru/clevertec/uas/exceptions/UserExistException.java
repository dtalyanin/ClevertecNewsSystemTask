package ru.clevertec.uas.exceptions;

import lombok.Getter;

@Getter
public class UserExistException extends IllegalArgumentException {
    private final String existedName;
    private final ErrorCode errorCode;

    public UserExistException(String s, String existedName, ErrorCode errorCode) {
        super(s);
        this.existedName = existedName;
        this.errorCode = errorCode;
    }
}
