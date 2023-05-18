package ru.clevertec.users.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.clevertec.exceptions.exceptions.AuthenticationException;
import ru.clevertec.users.dto.authentication.TokenDto;
import ru.clevertec.users.security.services.JwtService;

import static generators.factories.AuthenticationDtoFactory.getWithCorrectUsernameAndPassword;
import static generators.factories.AuthenticationDtoFactory.getWithIncorrectUsername;
import static generators.factories.TokenDtoFactory.getTokenDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static ru.clevertec.users.utils.constants.MessageConstants.INCORRECT_AUTH_DATA;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl service;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void checkAuthenticateShouldReturnToken() {
        doReturn("token").when(jwtService).generateToken(anyString());

        TokenDto actualDto = service.authenticate(getWithCorrectUsernameAndPassword());
        TokenDto expectedDto = getTokenDto();

        assertThat(actualDto).isEqualTo(expectedDto);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(anyString());
    }

    @Test
    void checkAuthenticateShouldThrowAuthenticationException() {
        doThrow(BadCredentialsException.class).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThatThrownBy(() -> service.authenticate(getWithIncorrectUsername()))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage(INCORRECT_AUTH_DATA);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(anyString());
    }
}