package ru.clevertec.users.integration.security.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.clevertec.users.integration.BaseIntegrationTest;
import ru.clevertec.users.security.services.impl.UserDetailServiceImpl;

import static generators.factories.UserFactory.getSubscriber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.clevertec.users.utils.constants.MessageConstants.USERNAME_NOT_FOUND;

class UserDetailServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private UserDetailServiceImpl service;

    @Test
    void checkLoadUserByUsernameShouldReturnWithExistingUser() {
        UserDetails actualUser =  service.loadUserByUsername("User");
        String expectedUsername = getSubscriber().getUsername();
        String expectedPassword = getSubscriber().getPassword();

        assertThat(actualUser.getUsername()).isEqualTo(expectedUsername);
        assertThat(actualUser.getPassword()).isEqualTo(expectedPassword);
    }

    @Test
    void checkLoadUserByUsernameShouldThrowExceptionUsernameNotExist() {
        assertThatThrownBy(() -> service.loadUserByUsername("UserNotExist"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(USERNAME_NOT_FOUND);
    }
}