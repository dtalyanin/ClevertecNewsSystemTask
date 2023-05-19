package ru.clevertec.users.services;

import ru.clevertec.users.dto.authentication.AuthenticationDto;
import ru.clevertec.users.dto.authentication.TokenDto;

/**
 * Service for authenticating and generating token for authenticated user
 */
public interface AuthenticationService {

    /**
     * authenticate user by params and return str token
     * @param request request with params for authentication
     * @return token DTO with generated str token
     */
    TokenDto authenticate(AuthenticationDto request);
}
