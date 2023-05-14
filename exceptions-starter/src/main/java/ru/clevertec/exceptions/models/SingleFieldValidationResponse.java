package ru.clevertec.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleFieldValidationResponse extends ValidationResponse {

    private final Object invalidValue;
    private final String errorMessage;
    private final int errorCode;
}
