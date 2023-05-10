package ru.clevertec.uas.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INCORRECT_VALUE(400),
    INCORRECT_AUTHENTICATION_DATA(404),
    USER_ID_NOT_FOUND(40401),
    USERNAME_NOT_FOUND(40402),
    USER_EXIST(422),
    TOKEN_INCORRECT_DATA(40001),
    TOKEN_EXPIRED(40002);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
