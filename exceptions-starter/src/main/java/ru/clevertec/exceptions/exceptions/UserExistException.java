package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

/**
 * User with similar username already exist id DB exception
 */
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
