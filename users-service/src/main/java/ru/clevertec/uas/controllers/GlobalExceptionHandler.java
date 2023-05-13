package ru.clevertec.uas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.uas.exceptions.AuthenticationException;
import ru.clevertec.uas.exceptions.ErrorCode;
import ru.clevertec.uas.exceptions.UserExistException;
import ru.clevertec.uas.exceptions.UserNotFoundException;
import ru.clevertec.uas.models.responses.ErrorResponse;
import ru.clevertec.uas.models.responses.IncorrectValueErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception, when cannot read value (for example cannot get Enum constant)
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpMessageNotReadableException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), ErrorCode.INCORRECT_VALUE.getCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode().getCode());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<IncorrectValueErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        IncorrectValueErrorResponse response =
                new IncorrectValueErrorResponse(e.getMessage(), e.getErrorCode().getCode(), e.getIncorrectValue());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<IncorrectValueErrorResponse> handleUserUserExistException(UserExistException e) {
        IncorrectValueErrorResponse response =
                new IncorrectValueErrorResponse(e.getMessage(), e.getErrorCode().getCode(), e.getExistedName());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
