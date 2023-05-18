package ru.clevertec.news.utils;

import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.models.ErrorCode;

import static ru.clevertec.news.utils.constants.MessageConstants.INCORRECT_TOKEN;

/**
 * Class for get token from bearer token
 */
public class JwtTokenHelper {

    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Get bearer token, check if it has correct structure and return directly str token
     * @param authHeader authentication header from request
     * @return str token
     */
    public static String getJwtTokenFromAuthHeader(String authHeader) {
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new TokenException(INCORRECT_TOKEN, ErrorCode.INCORRECT_TOKEN);
        }
        return authHeader.substring(BEARER_PREFIX.length());
    }
}
