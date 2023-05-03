package ru.clevertec.uas.exceptions;

import lombok.Getter;

@Getter
public class UserExistException extends IllegalArgumentException {
    private final String existedName;

    public UserExistException(String s, String existedName) {
        super(s);
        this.existedName = existedName;
    }
}
