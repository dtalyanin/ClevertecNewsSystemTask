package ru.clevertec.exceptions.utils;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.MultipleFieldsValidationResponse;
import ru.clevertec.exceptions.models.SingleFieldValidationResponse;
import ru.clevertec.exceptions.models.ValidationResponse;


import java.util.List;
import java.util.Set;

public class ValidationResponsesFactory {

    public static ValidationResponse getResponseFromConstraints(Set<ConstraintViolation<?>> constraintViolations) {
        if (constraintViolations.size() == 1) {
            ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
            return getResponseFromSingleErrorField(constraintViolation);
        } else {
            return getResponseFromMultipleErrorFields(constraintViolations);
        }
    }

    public static ValidationResponse getResponseFromErrors(List<FieldError> errors) {
        if (errors.size() == 1) {
            FieldError error = errors.get(0);
            return getResponseFromSingleErrorField(error);
        } else {
            return getResponseFromMultipleErrorFields(errors);
        }
    }

    private static SingleFieldValidationResponse getResponseFromSingleErrorField(ConstraintViolation<?> cv) {
        return new SingleFieldValidationResponse(
                cv.getInvalidValue(),
                cv.getMessage(),
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    private static SingleFieldValidationResponse getResponseFromSingleErrorField(FieldError error) {
        return new SingleFieldValidationResponse(
                error.getRejectedValue(),
                error.getDefaultMessage(),
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    private static MultipleFieldsValidationResponse getResponseFromMultipleErrorFields(List<FieldError> errors) {
        List<SingleFieldValidationResponse> validationResponses = errors.stream()
                .map(ValidationResponsesFactory::getResponseFromSingleErrorField)
                .toList();
        return new MultipleFieldsValidationResponse(validationResponses);
    }

    private static MultipleFieldsValidationResponse getResponseFromMultipleErrorFields(
            Set<ConstraintViolation<?>> constraintViolations) {
        List<SingleFieldValidationResponse> validationResponses = constraintViolations.stream()
                .map(ValidationResponsesFactory::getResponseFromSingleErrorField)
                .toList();
        return new MultipleFieldsValidationResponse(validationResponses);
    }
}
