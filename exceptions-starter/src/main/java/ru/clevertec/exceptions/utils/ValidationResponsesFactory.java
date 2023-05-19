package ru.clevertec.exceptions.utils;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.MultipleFieldsValidationResponse;
import ru.clevertec.exceptions.models.SingleFieldValidationResponse;
import ru.clevertec.exceptions.models.ValidationResponse;


import java.util.List;
import java.util.Set;

/**
 * Class that provide generating validationResponse depends on error fields
 */
public class ValidationResponsesFactory {

    /**
     * Create response from set of constraint violations that were cause of exception
     * @param constraintViolations Violations that were cause of exception
     * @return Validation response of exception reason
     */
    public static ValidationResponse getResponseFromConstraints(Set<ConstraintViolation<?>> constraintViolations) {
        if (constraintViolations.size() == 1) {
            ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
            return getResponseFromSingleErrorField(constraintViolation);
        } else {
            return getResponseFromMultipleErrorFields(constraintViolations);
        }
    }

    /**
     * Create response from field errors that were cause of exception
     * @param errors Errors that were cause of exception
     * @return Validation response of exception reason
     */
    public static ValidationResponse getResponseFromErrors(List<FieldError> errors) {
        if (errors.size() == 1) {
            FieldError error = errors.get(0);
            return getResponseFromSingleErrorField(error);
        } else {
            return getResponseFromMultipleErrorFields(errors);
        }
    }

    /**
     * Create response from constraint violation that was cause of exception
     * @param cv Violation that was cause of exception
     * @return Validation response of invalid value that was reason of exception
     */
    private static SingleFieldValidationResponse getResponseFromSingleErrorField(ConstraintViolation<?> cv) {
        return new SingleFieldValidationResponse(
                cv.getInvalidValue(),
                cv.getMessage(),
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    /**
     * Create response from field error that was cause of exception
     * @param error Error that was cause of exception
     * @return Validation response of invalid value that was reason of exception
     */
    private static SingleFieldValidationResponse getResponseFromSingleErrorField(FieldError error) {
        return new SingleFieldValidationResponse(
                error.getRejectedValue(),
                error.getDefaultMessage(),
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    /**
     * Create response from several field errors that were cause of exception
     * @param errors Errors that was cause of exception
     * @return Validation response of invalid values that were reason of exception
     */
    private static MultipleFieldsValidationResponse getResponseFromMultipleErrorFields(List<FieldError> errors) {
        List<SingleFieldValidationResponse> validationResponses = errors.stream()
                .map(ValidationResponsesFactory::getResponseFromSingleErrorField)
                .toList();
        return new MultipleFieldsValidationResponse(validationResponses);
    }

    /**
     * Create response from several constraints violations that were cause of exception
     * @param constraintViolations Violations that were cause of exception
     * @return Validation response of invalid values that were reason of exception
     */
    private static MultipleFieldsValidationResponse getResponseFromMultipleErrorFields(
            Set<ConstraintViolation<?>> constraintViolations) {
        List<SingleFieldValidationResponse> validationResponses = constraintViolations.stream()
                .map(ValidationResponsesFactory::getResponseFromSingleErrorField)
                .toList();
        return new MultipleFieldsValidationResponse(validationResponses);
    }
}
