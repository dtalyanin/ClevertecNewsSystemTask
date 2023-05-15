package ru.clevertec.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultipleFieldsValidationResponse extends ValidationResponse {
    private final List<SingleFieldValidationResponse> errors;
}