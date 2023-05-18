package ru.clevertec.users.security.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.models.User;

import java.util.Optional;

import static generators.factories.UserFactory.getAdmin;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.users.utils.constants.MessageConstants.USERNAME_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

    @InjectMocks
    private UserDetailServiceImpl service;
    @Mock
    private UsersRepository repository;

    @Test
    void checkLoadUserByUsernameShouldReturnByName() {
        User admin = getAdmin();

        doReturn(Optional.of(admin)).when(repository).findByUsername(anyString());
        UserDetails actualDetails = service.loadUserByUsername("User");

        assertAll(
                () -> assertThat(actualDetails.getUsername()).isEqualTo(admin.getUsername()),
                () -> assertThat(actualDetails.getPassword()).isEqualTo(admin.getPassword()));

        verify(repository).findByUsername(anyString());
    }

    @Test
    void checkLoadUserByUsernameShouldThrowUsernameNotFoundException() {
        doReturn(Optional.empty()).when(repository).findByUsername(anyString());

        assertThatThrownBy(() -> service.loadUserByUsername("User"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(USERNAME_NOT_FOUND);

        verify(repository).findByUsername(anyString());
    }
}