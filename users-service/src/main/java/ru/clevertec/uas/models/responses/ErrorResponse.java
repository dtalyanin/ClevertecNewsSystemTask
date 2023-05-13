package ru.clevertec.uas.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String errorMessage;
    private int errorCode;
}
