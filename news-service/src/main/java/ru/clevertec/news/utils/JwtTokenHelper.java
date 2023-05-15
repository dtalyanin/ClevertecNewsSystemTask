package ru.clevertec.news.utils;

import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.models.ErrorCode;

import static ru.clevertec.news.utils.constants.MessageConstants.INCORRECT_TOKEN;

public class JwtTokenHelper {
    private static final String BEARER_PREFIX = "Bearer ";

    public static String getJwtTokenFromAuthHeader(String authHeader) {
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new TokenException(INCORRECT_TOKEN, ErrorCode.INCORRECT_TOKEN);
        }
        return authHeader.substring(BEARER_PREFIX.length());
    }
}
