package ru.clevertec.users.integration.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.exceptions.exceptions.AuthenticationException;
import ru.clevertec.users.dto.authentication.TokenDto;
import ru.clevertec.users.integration.BaseIntegrationTest;
import ru.clevertec.users.services.impl.AuthenticationServiceImpl;

import static generators.factories.AuthenticationDtoFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class AuthenticationServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private AuthenticationServiceImpl service;

    @Test
    void checkAuthenticateShouldReturnToken() {
        TokenDto actualToken = service.authenticate(getWithCorrectUsernameAndPassword());

        assertThat(actualToken.token()).isNotBlank();
    }

    @Test
    void checkAuthenticateShouldThrowAuthenticationExceptionIncorrectPassword() {
        assertThatThrownBy(() -> service.authenticate(getWithIncorrectPassword()))
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void checkAuthenticateShouldThrowAuthenticationExceptionIncorrectName() {
        assertThatThrownBy(() -> service.authenticate(getWithIncorrectUsername()))
                .isInstanceOf(AuthenticationException.class);
    }
}