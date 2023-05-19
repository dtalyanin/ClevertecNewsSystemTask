package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

/**
 * Token not valid (expired, incorrect structure) exception
 */
@Getter
public class TokenException extends IllegalArgumentException {

    private final ErrorCode errorCode;

    public TokenException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }
}
