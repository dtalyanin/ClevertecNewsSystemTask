package ru.clevertec.exceptions.exceptions;

import lombok.Getter;

/**
 * Cache implementation creating exception
 */
@Getter
public class CacheException extends IllegalArgumentException {

    public CacheException(String s) {
        super(s);
    }
}
