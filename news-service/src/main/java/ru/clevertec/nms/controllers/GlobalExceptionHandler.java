package ru.clevertec.nms.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.responses.NotFoundResponse;
import ru.clevertec.nms.models.responses.validation_responses.ValidationResponse;

import static ru.clevertec.nms.utils.ValidationResponsesFactory.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationResponse> findValidationExceptionInParameters(ConstraintViolationException e) {
        ValidationResponse response = getResponseFromConstraints(e.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleMethodValidationException(MethodArgumentNotValidException e) {
        ValidationResponse response = getResponseFromErrors(e.getFieldErrors(), e.getTarget().getClass());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundResponse> handleNotFoundException(NotFoundException e) {
        NotFoundResponse response = new NotFoundResponse(e.getIncorrectId(), e.getMessage(), e.getErrorCode().getCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
