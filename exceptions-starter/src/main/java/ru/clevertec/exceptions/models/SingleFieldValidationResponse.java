package ru.clevertec.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response with incorrect value, exception message and int code of exception
 */
@Getter
@AllArgsConstructor
public class SingleFieldValidationResponse extends ValidationResponse {

    private final Object invalidValue;
    private final String errorMessage;
    private final int errorCode;
}
