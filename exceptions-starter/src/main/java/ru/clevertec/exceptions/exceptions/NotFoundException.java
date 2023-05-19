package ru.clevertec.exceptions.exceptions;

import lombok.Getter;
import ru.clevertec.exceptions.models.ErrorCode;

/**
 * Searched item not found
 */
@Getter
public class NotFoundException extends IllegalArgumentException {
    private final Object incorrectValue;
    private final ErrorCode errorCode;

    public NotFoundException(String message, Object incorrectValue, ErrorCode errorCode) {
        super(message);
        this.incorrectValue = incorrectValue;
        this.errorCode = errorCode;
    }
}
