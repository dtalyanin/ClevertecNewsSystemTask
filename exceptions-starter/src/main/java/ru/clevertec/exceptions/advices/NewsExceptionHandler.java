package ru.clevertec.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.exceptions.exceptions.AccessException;
import ru.clevertec.exceptions.exceptions.UsersClientException;
import ru.clevertec.exceptions.models.ErrorResponse;

/**
 * Advice for handling exception thrown by application
 */
@ControllerAdvice
public class NewsExceptionHandler {

    /**
     * Handle exception when user doesn't have permissions for executing operation
     * @param e Caught exception
     * @return Error response with message end error code
     */
    @ExceptionHandler(AccessException.class)
    public ResponseEntity<ErrorResponse> handleAccessException(AccessException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    /**
     * Handle exception with external client
     * @param e Caught exception
     * @return Error response with message end error code
     */
    @ExceptionHandler(UsersClientException.class)
    public ResponseEntity<ErrorResponse> handleUsersClientException(UsersClientException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(response);
    }
}
