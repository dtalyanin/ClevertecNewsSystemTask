package ru.clevertec.exceptions.advices;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.ErrorResponse;
import ru.clevertec.exceptions.models.IncorrectValueErrorResponse;
import ru.clevertec.exceptions.models.ValidationResponse;

import static ru.clevertec.exceptions.utils.ValidationResponsesFactory.getResponseFromConstraints;
import static ru.clevertec.exceptions.utils.ValidationResponsesFactory.getResponseFromErrors;
import static ru.clevertec.exceptions.utils.constants.MessageConstants.HEADER_NOT_PRESENT;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception, when cannot read value (for example cannot get Enum constant)
     *
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpMessageNotReadableException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), ErrorCode.INCORRECT_FIELD_VALUE.getCode());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ValidationResponse response = getResponseFromConstraints(e.getConstraintViolations());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleMethodValidationException(MethodArgumentNotValidException e) {
        ValidationResponse response = getResponseFromErrors(e.getFieldErrors());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        String message = e.getHeaderName() + HEADER_NOT_PRESENT;
        ErrorResponse response = new ErrorResponse(message, ErrorCode.NO_AUTH_HEADER.getCode());
        return ResponseEntity
                .badRequest()
                .body(response);
    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
//        ErrorResponse response = new ErrorResponse(e.getMessage(), ErrorCode.GENERAL_EXCEPTION.getCode());
//        return ResponseEntity
//                .internalServerError()
//                .body(response);
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<IncorrectValueErrorResponse> handleUserNotFoundException(NotFoundException e) {
        IncorrectValueErrorResponse response = new IncorrectValueErrorResponse(
                e.getMessage(),
                e.getIncorrectValue(),
                e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}