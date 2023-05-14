package ru.clevertec.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.exceptions.exceptions.AccessException;
import ru.clevertec.exceptions.exceptions.UsersClientException;
import ru.clevertec.exceptions.models.ErrorResponse;

@ControllerAdvice
public class NewsExceptionHandler {

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<ErrorResponse> handleAccessException(AccessException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode().getCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(UsersClientException.class)
    public ResponseEntity<ErrorResponse> handleUsersClientException(UsersClientException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), e.getErrorCode());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(response);
    }
}
