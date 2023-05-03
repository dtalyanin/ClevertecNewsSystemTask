package ru.clevertec.nms.models.responses.validation_responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleFieldValidationResponse extends ValidationResponse {
    private Object invalidValue;
    private String errorMessage;
    private int errorCode;
}
