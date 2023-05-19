package ru.clevertec.news.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.exceptions.exceptions.TokenException;

import static generators.factories.BearerTokenFactory.getAdminToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.clevertec.news.utils.JwtTokenHelper.getJwtTokenFromAuthHeader;
import static ru.clevertec.news.utils.constants.MessageConstants.INCORRECT_TOKEN;

class JwtTokenHelperTest {

    @Test
    void checkGetJwtTokenFromAuthHeaderShouldReturnTokenFromBearerStr() {
        String actualToken = getJwtTokenFromAuthHeader(getAdminToken());
        String expectedToken = "admin";

        assertThat(actualToken).isEqualTo(expectedToken);
    }

    @Test
    void checkGetJwtTokenFromAuthHeaderShouldThrowExceptionTokenNull() {

        assertThatThrownBy(() -> getJwtTokenFromAuthHeader(null))
                .isInstanceOf(TokenException.class)
                .hasMessage(INCORRECT_TOKEN);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "admin", "Beareradmin"})
    void checkGetJwtTokenFromAuthHeaderShouldThrowExceptionNotCorrectToken(String token) {

        assertThatThrownBy(() -> getJwtTokenFromAuthHeader(token))
                .isInstanceOf(TokenException.class)
                .hasMessage(INCORRECT_TOKEN);
    }
}