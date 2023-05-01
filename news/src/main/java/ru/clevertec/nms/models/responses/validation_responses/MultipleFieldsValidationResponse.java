package ru.clevertec.nms.models.responses.validation_responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultipleFieldsValidationResponse extends ValidationResponse {
    private final List<SingleFieldValidationResponse> errors;
}