package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public class CacheException extends IllegalArgumentException {

    public CacheException(String s) {
        super(s);
    }
}
