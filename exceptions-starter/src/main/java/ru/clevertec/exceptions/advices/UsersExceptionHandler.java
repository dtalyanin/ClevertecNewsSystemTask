package ru.clevertec.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.exceptions.exceptions.AuthenticationException;
import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.exceptions.UserExistException;
import ru.clevertec.exceptions.models.ErrorResponse;
import ru.clevertec.exceptions.models.IncorrectValueErrorResponse;

@ControllerAdvice
public class UsersExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<IncorrectValueErrorResponse> handleUserUserExistException(UserExistException e) {
        IncorrectValueErrorResponse response = new IncorrectValueErrorResponse(
                e.getMessage(),
                e.getExistedName(),
                e.getErrorCode().getCode());
        return ResponseEntity
                .unprocessableEntity()
                .body(response);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }
}
