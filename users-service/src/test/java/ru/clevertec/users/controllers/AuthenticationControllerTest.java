package ru.clevertec.users.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.clevertec.users.dto.authentication.AuthenticationDto;
import ru.clevertec.users.dto.authentication.TokenDto;
import ru.clevertec.users.services.AuthenticationService;

import static generators.factories.AuthenticationDtoFactory.getWithCorrectUsernameAndPassword;
import static generators.factories.TokenDtoFactory.getTokenDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController controller;
    @Mock
    private AuthenticationService service;

    @Test
    void checkAuthenticateShouldReturnToken() {
        doReturn(getTokenDto()).when(service).authenticate(any(AuthenticationDto.class));

        ResponseEntity<TokenDto> actualResponse = controller.authenticate(getWithCorrectUsernameAndPassword());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        TokenDto actualBody = actualResponse.getBody();

        TokenDto expectedBody = getTokenDto();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(service).authenticate(any(AuthenticationDto.class));
    }
}