package ru.clevertec.exceptions.models;

import lombok.Getter;

/**
 * Response with incorrect value, that caused exception
 */
@Getter
public class IncorrectValueErrorResponse extends ErrorResponse {

    private final Object incorrectValue;

    public IncorrectValueErrorResponse(String errorMessage, Object incorrectValue, int errorCode) {
        super(errorMessage, errorCode);
        this.incorrectValue = incorrectValue;
    }
}
