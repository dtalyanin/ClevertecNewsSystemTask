package ru.clevertec.nms.utils;

import jakarta.validation.ConstraintViolation;
import lombok.experimental.UtilityClass;
import org.springframework.validation.FieldError;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.models.responses.validation_responses.MultipleFieldsValidationResponse;
import ru.clevertec.nms.models.responses.validation_responses.SingleFieldValidationResponse;
import ru.clevertec.nms.models.responses.validation_responses.ValidationResponse;

import java.util.List;
import java.util.Set;

@UtilityClass
public class ValidationResponsesFactory {

    public ValidationResponse getResponseFromConstraints(Set<ConstraintViolation<?>> constraintViolations) {
        if (constraintViolations.size() == 1) {
            ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
            return getSingleFieldValidationResponse(constraintViolation);
        } else {
            return getMultipleFieldsValidationResponse(constraintViolations);
        }
    }

    public ValidationResponse getResponseFromErrors(List<FieldError> errors, Class<?> targetClass) {
        if (errors.size() == 1) {
            FieldError error = errors.get(0);
            return getSingleFieldValidationResponse(error, targetClass);
        } else {
            return getMultipleFieldsValidationResponse(errors, targetClass);
        }
    }

    private SingleFieldValidationResponse getSingleFieldValidationResponse(ConstraintViolation<?> cv) {
        return new SingleFieldValidationResponse(cv.getInvalidValue(), cv.getMessage(),
                getIntErrorCodeDependsOnCause(cv.getLeafBean().getClass()));
    }

    private SingleFieldValidationResponse getSingleFieldValidationResponse(FieldError error, Class<?> targetClass) {
        return new SingleFieldValidationResponse(error.getRejectedValue(), error.getDefaultMessage(),
                getIntErrorCodeDependsOnCause(targetClass));
    }

    private MultipleFieldsValidationResponse getMultipleFieldsValidationResponse(List<FieldError> errors,
                                                                                 Class<?> targetClass) {
        List<SingleFieldValidationResponse> validationResponses = errors.stream()
                .map(error -> getSingleFieldValidationResponse(error, targetClass))
                .toList();
        return new MultipleFieldsValidationResponse(validationResponses);
    }

    private MultipleFieldsValidationResponse getMultipleFieldsValidationResponse(
            Set<ConstraintViolation<?>> constraintViolations) {
        List<SingleFieldValidationResponse> validationResponses = constraintViolations.stream()
                .map(ValidationResponsesFactory::getSingleFieldValidationResponse)
                .toList();
        return new MultipleFieldsValidationResponse(validationResponses);
    }

    private int getIntErrorCodeDependsOnCause(Class<?> causeClass) {
        ErrorCode errorCode;
        if (causeClass == News.class) {
            errorCode = ErrorCode.INVALID_NEWS_FIELD_VALUE;
        } else if (causeClass == Comment.class) {
            errorCode = ErrorCode.INVALID_COMMENT_FIELD_VALUE;
        } else {
            errorCode = ErrorCode.INVALID_FIELD_VALUE;
        }
        return errorCode.getCode();
    }
}
