package ru.clevertec.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response with error message about exception and int code of exception
 */
@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String errorMessage;
    private int errorCode;
}