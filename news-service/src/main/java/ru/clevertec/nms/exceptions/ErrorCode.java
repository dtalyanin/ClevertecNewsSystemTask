package ru.clevertec.nms.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NEWS_NOT_FOUND(40401),
    INVALID_FIELD_VALUE(40000),
    INVALID_NEWS_FIELD_VALUE(40001),
    INVALID_COMMENT_FIELD_VALUE(40002),

    USER_NOT_FOUND(401)
    ;

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
