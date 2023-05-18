package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

@Getter
public class PasswordException extends IllegalArgumentException {

    private final Object invalidValue;
    private final ErrorCode errorCode;

    public PasswordException(String s, Object invalidValue, ErrorCode errorCode) {
        super(s);
        this.invalidValue = invalidValue;
        this.errorCode = errorCode;
    }
}
