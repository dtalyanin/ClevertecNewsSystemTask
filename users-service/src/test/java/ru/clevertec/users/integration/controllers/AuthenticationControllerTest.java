package ru.clevertec.users.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.users.integration.BaseIntegrationTest;

import static generators.factories.AuthenticationDtoFactory.*;
import static generators.factories.ErrorResponseFactory.getResponseWithAuthException;
import static generators.factories.ValidationResponseFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class AuthenticationControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @SneakyThrows
    void checkAuthenticateShouldReturnTokenDto() {
        String authJson = mapper.writeValueAsString(getWithCorrectUsernameAndPassword());

        mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(authJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void checkAuthenticateShouldReturnErrorResponseWhenEmptyUsername() {
        String authJson = mapper.writeValueAsString(getWithEmptyUsername());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyUsername());

        mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(authJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAuthenticateShouldReturnErrorResponseWhenEmptyPassword() {
        String authJson = mapper.writeValueAsString(getWithEmptyPassword());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyPassword());

        mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(authJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAuthenticateShouldReturnErrorResponseWhenToSmallPassword() {
        String authJson = mapper.writeValueAsString(getWithTooSmallPassword());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseTooSmallPassword());

        mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(authJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAuthenticateShouldReturnErrorResponseWhenIncorrectUsername() {
        String authJson = mapper.writeValueAsString(getWithIncorrectUsername());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithAuthException());

        mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                .content(authJson))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAuthenticateShouldReturnErrorResponseWhenIncorrectPassword() {
        String authJson = mapper.writeValueAsString(getWithIncorrectPassword());
        String errorResponse = mapper.writeValueAsString(getResponseWithAuthException());

        mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(authJson))
                .andExpect(status().isForbidden())
                .andExpect(content().json(errorResponse));
    }
}