package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

/**
 * User not authenticated exception
 */
@Getter
public class AuthenticationException extends IllegalArgumentException {

    private final ErrorCode errorCode;

    public AuthenticationException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }
}
