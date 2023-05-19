package ru.clevertec.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.exceptions.exceptions.AuthenticationException;
import ru.clevertec.exceptions.exceptions.PasswordException;
import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.exceptions.UserExistException;
import ru.clevertec.exceptions.models.ErrorResponse;
import ru.clevertec.exceptions.models.IncorrectValueErrorResponse;
import ru.clevertec.exceptions.models.SingleFieldValidationResponse;
import ru.clevertec.exceptions.models.ValidationResponse;

/**
 * Advice for handling exception thrown by application
 */
@ControllerAdvice
public class UsersExceptionHandler {

    /**
     * Handle exception when user cannot be authenticated
     * @param e Caught exception
     * @return Error response with message end error code
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    /**
     * Handle exception when user with similar username already exist id DB
     * @param e Caught exception
     * @return Error response with message end error code
     */
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

    /**
     * Handle exception when token not valid (expired, incorrect structure)
     * @param e Caught exception
     * @return Error response with message end error code
     */
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    /**
     * Handle exception when password is incorrect for encoding (null or blank password)
     * @param e Caught exception
     * @return Error response with incorrect value, message end error code
     */
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ValidationResponse> handlePasswordException(PasswordException e) {
        SingleFieldValidationResponse response = new SingleFieldValidationResponse(
                e.getInvalidValue(),
                e.getMessage(),
                e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
