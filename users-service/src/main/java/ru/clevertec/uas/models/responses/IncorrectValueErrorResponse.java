package ru.clevertec.uas.models.responses;

import lombok.Getter;

@Getter
public class IncorrectValueErrorResponse extends ErrorResponse {
    private final Object incorrectValue;

    public IncorrectValueErrorResponse(String errorMessage, int errorCode, Object incorrectValue) {
        super(errorMessage, errorCode);
        this.incorrectValue = incorrectValue;
    }
}
