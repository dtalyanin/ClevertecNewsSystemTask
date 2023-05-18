package ru.clevertec.users.security.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.exceptions.exceptions.TokenException;

import static generators.factories.TokenFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
class JwtServiceImplTest {

    private final String secretKey = "586E327235753778214125442A472D4B6150645367566B597033733676397924";
    private final long tokenLifetime = 86400000;
    private final JwtServiceImpl service = new JwtServiceImpl(secretKey, tokenLifetime);

    @Test
    void checkExtractUsernameShouldReturnCorrectName() {
        String actualUsername = service.extractUsername(getCorrectSubscriberToken(secretKey, tokenLifetime));
        String expectedUsername = "User";

        assertThat(actualUsername).isEqualTo(expectedUsername);
    }

    @Test
    void checkExtractUsernameShouldThrowExceptionIncorrectToken() {
        assertThatThrownBy(() -> service.extractUsername(getIncorrectToken(secretKey, tokenLifetime)))
                .isInstanceOf(TokenException.class);
    }

    @Test
    void checkGenerateTokenShouldReturnNotBlankToken() {
        String actualToken = service.generateToken("User");

        assertThat(actualToken).isNotBlank();
    }

    @Test
    void checkIsTokenValidShouldReturnTrue() {
        boolean actual = service.isTokenValid(getCorrectSubscriberToken(secretKey, tokenLifetime), "User");

        assertThat(actual).isTrue();
    }

    @Test
    void checkIsTokenValidShouldReturnFalseNotSimilarUsername() {
        boolean actual = service.isTokenValid(getTokenWithNotExistingUsername(secretKey, tokenLifetime), "User");

        assertThat(actual).isFalse();
    }

    @Test
    void checkIsTokenValidShouldThrowExceptionTokenExpired() {
        assertThatThrownBy(() -> service.isTokenValid(getTokenWithExpiredDate(secretKey, tokenLifetime), "User"))
                .isInstanceOf(TokenException.class);
    }

    @Test
    void checkIsTokenExpiredShouldBeFalse() {
        boolean actual = service.isTokenExpired(getCorrectSubscriberToken(secretKey, tokenLifetime));

        assertThat(actual).isFalse();
    }

    @Test
    void checkIsTokenExpiredShouldThrowExceptionTokenExpired() {
        assertThatThrownBy(() -> service.isTokenExpired(getTokenWithExpiredDate(secretKey, tokenLifetime)))
                .isInstanceOf(TokenException.class);
    }
}