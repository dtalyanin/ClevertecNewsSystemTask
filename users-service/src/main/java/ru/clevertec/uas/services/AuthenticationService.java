package ru.clevertec.uas.services;

import ru.clevertec.uas.dto.authentication.AuthenticationDto;
import ru.clevertec.uas.dto.authentication.TokenDto;

public interface AuthenticationService {
    TokenDto authenticate(AuthenticationDto request);
}
