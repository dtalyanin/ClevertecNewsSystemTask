package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UsersClientException extends RuntimeException {
    private final Integer errorCode;
    private final HttpStatus httpStatus;

    public UsersClientException(String message, Integer errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
