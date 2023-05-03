package ru.clevertec.uas.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends IllegalArgumentException {
    private final Object incorrectPath;

    public UserNotFoundException(String s, Object incorrectPath) {
        super(s);
        this.incorrectPath = incorrectPath;
    }
}
