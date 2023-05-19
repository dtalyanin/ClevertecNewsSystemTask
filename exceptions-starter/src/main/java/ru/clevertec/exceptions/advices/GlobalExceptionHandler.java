package ru.clevertec.exceptions.advices;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.exceptions.exceptions.FieldException;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.ErrorResponse;
import ru.clevertec.exceptions.models.IncorrectValueErrorResponse;
import ru.clevertec.exceptions.models.ValidationResponse;

import static ru.clevertec.exceptions.utils.ValidationResponsesFactory.getResponseFromConstraints;
import static ru.clevertec.exceptions.utils.ValidationResponsesFactory.getResponseFromErrors;
import static ru.clevertec.exceptions.utils.constants.MessageConstants.HEADER_NOT_PRESENT;

/**
 * Advice for handling global exception thrown by application
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception, when cannot read value (for example cannot get Enum constant)
     * @param e Caught exception
     * @return Error response with message end error code
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpMessageNotReadableException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), ErrorCode.INCORRECT_FIELD_VALUE.getCode());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * Handle validation exception that reports result of constraint violations
     * @param e Caught exception
     * @return Error response with incorrect value, message end error code
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ValidationResponse response = getResponseFromConstraints(e.getConstraintViolations());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * Handle validation exception when method argument not valid
     * @param e Caught exception
     * @return Error response with incorrect value, message end error code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleMethodValidationException(MethodArgumentNotValidException e) {
        ValidationResponse response = getResponseFromErrors(e.getFieldErrors());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * Handle exception when required header is missing
     * @param e Caught exception
     * @return Error response with message end error code
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        String message = e.getHeaderName() + HEADER_NOT_PRESENT;
        ErrorResponse response = new ErrorResponse(message, ErrorCode.NO_AUTH_HEADER.getCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    /**
     * Handle exception when searched item not found
     * @param e Caught exception
     * @return Error response with message end error code
     */
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

    /**
     * Handle exception when cannot get object field with reflection
     * @param e Caught exception
     * @return Error response with message end error code
     */
    @ExceptionHandler(FieldException.class)
    public ResponseEntity<ErrorResponse> handleFieldException(FieldException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}