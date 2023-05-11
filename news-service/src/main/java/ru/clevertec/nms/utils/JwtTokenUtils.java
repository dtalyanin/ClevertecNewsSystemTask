package ru.clevertec.nms.utils;

import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.TokenException;

import static ru.clevertec.nms.utils.constants.MessageConstants.INCORRECT_TOKEN;

public class JwtTokenUtils {
    private static final String BEARER_PREFIX = "Bearer ";

    public String getJwtToken(String authHeader) {
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new TokenException(INCORRECT_TOKEN, ErrorCode.INCORRECT_TOKEN);
        }
        return authHeader.substring(BEARER_PREFIX.length());
    }
}
