package ru.clevertec.exceptions.models;

import lombok.Getter;

@Getter
public class IncorrectValueErrorResponse extends ErrorResponse {

    private final Object incorrectValue;

    public IncorrectValueErrorResponse(String errorMessage, Object incorrectValue, int errorCode) {
        super(errorMessage, errorCode);
        this.incorrectValue = incorrectValue;
    }
}
