package ru.clevertec.users.services;

import ru.clevertec.users.dto.authentication.AuthenticationDto;
import ru.clevertec.users.dto.authentication.TokenDto;

public interface AuthenticationService {
    TokenDto authenticate(AuthenticationDto request);
}
