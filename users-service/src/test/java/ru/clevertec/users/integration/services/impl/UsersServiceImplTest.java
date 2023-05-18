package ru.clevertec.users.integration.services.impl;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.exceptions.PasswordException;
import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.exceptions.UserExistException;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.integration.BaseIntegrationTest;
import ru.clevertec.users.models.Role;
import ru.clevertec.users.services.impl.UsersServiceImpl;

import java.util.List;

import static generators.factories.CreateDtoFactory.*;
import static generators.factories.PageableFactory.*;
import static generators.factories.TokenFactory.*;
import static generators.factories.UpdateDtoFactory.*;
import static generators.factories.UserDtoFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.clevertec.users.utils.constants.MessageConstants.*;

class UsersServiceImplTest extends BaseIntegrationTest {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.lifetime}")
    private long tokenLifetime;

    @Autowired
    private UsersServiceImpl service;
    @Autowired
    private UsersRepository repository;

    @Test
    void checkGetAllUsersWithPaginationShouldReturnAll5Users() {
        List<UserDto> actualUsers = service.getAllUsersWithPagination(getDefaultPageable());
        List<UserDto> expectedUsers = getAllUserDtos();

        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    void checkGetAllUsersWithPaginationShouldReturn2UsersWithPageSize2() {
        List<UserDto> actualUsers = service.getAllUsersWithPagination(getPageableWithSize2());
        List<UserDto> expectedUsers = getFirst2UserDtos();

        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    void checkGetAllUsersWithPaginationShouldReturn2UsersFrom2PageAndSize2() {
        List<UserDto> actualUsers = service.getAllUsersWithPagination(getPageableWithPage2AndSize2());
        List<UserDto> expectedUsers = getFrom2PageFirst2UserDtos();

        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    void checkGetAllUsersWithPaginationShouldReturnEmptyListWhenOutOfRange() {
        List<UserDto> actualUsers = service.getAllUsersWithPagination(getPageableOutOfRange());
        List<UserDto> expectedUsers = getEmptyListUserDtos();

        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    void checkGetAllUsersWithPaginationShouldReturnAll5UsersWithoutSort() {
        List<UserDto> actualUsers = service.getAllUsersWithPagination(getDefaultPageableWithSortingByUsernameDesc());
        List<UserDto> expectedUsers = getAllUserDtos();

        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    void checkGetUserByIdShouldReturnExistingUser() {
        UserDto actualUser = service.getUserById(1L);
        UserDto expectedUser = getSubscriberDto();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void checkGetUserByIdShouldThrowNotFoundException() {
        assertThatThrownBy(() -> service.getUserById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(USER_ID_NOT_FOUND);
    }

    @Test
    void checkGetUserByTokenShouldReturnUser() {
        String token = getCorrectSubscriberToken(secretKey, tokenLifetime);
        UserDto actualUser = service.getUserByToken(token);
        UserDto expectedUser = getSubscriberDto();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void checkGetUserByTokenShouldThrowTokenExceptionWhenNoUserFound() {
        String token = getTokenWithNotExistingUsername(secretKey, tokenLifetime);
        assertThatThrownBy(() -> service.getUserByToken(token))
                .isInstanceOf(TokenException.class);
    }

    @Test
    void checkGetUserByTokenShouldThrowTokenExceptionWhenTokenExpired() {
        String token = getTokenWithExpiredDate(secretKey, tokenLifetime);
        assertThatThrownBy(() -> service.getUserByToken(token))
                .isInstanceOf(TokenException.class);
    }

    @Test
    void checkGetUserByTokenShouldThrowTokenExceptionWhenTokenNotCorrect() {
        String token = getIncorrectToken(secretKey, tokenLifetime);
        assertThatThrownBy(() -> service.getUserByToken(token))
                .isInstanceOf(TokenException.class);
    }

    @Test
    void checkAddUserShouldReturnUserWithGeneratedId() {
        UserDto actualUser = service.addUser(getCreateDto());
        UserDto expectedUser = getCreatedUserDto();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void checkAddUserShouldExistInDbAfterExecution() {
        long createdId = service.addUser(getCreateDto()).getId();

        assertThat(repository.findById(createdId)).isPresent();
    }

    @Test
    void checkAddUserShouldThrowExceptionUsernameExist() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithExistedInDbName()))
                .isInstanceOf(UserExistException.class)
                .hasMessage(USER_EXIST);
    }

    @Test
    void checkAddUserShouldThrowExceptionNullUsername() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithNullName()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddUserShouldThrowExceptionEmptyUsername() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithEmptyName()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddUserShouldThrowExceptionTooBigUsername() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithTooBigName()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddUserShouldThrowExceptionNullPassword() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithNullPassword()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddUserShouldThrowExceptionEmptyPassword() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithEmptyPassword()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddUserShouldThrowExceptionTooSmallPassword() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithTooSmallPassword()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddUserShouldThrowExceptionNullUsernameAndNullPassword() {
        assertThatThrownBy(() -> service.addUser(getCreateDtoWithNullNameAndNullPassword()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkUpdateUserShouldExistInDbWithUpdatedPassword() {
        long idForUpdate = 1L;
        String passwordBeforeUpdate = repository.findById(idForUpdate).get().getPassword();
        service.updateUser(idForUpdate, getUpdateDtoWithOnlyUpdatedPassword());
        String passwordAfterUpdate = repository.findById(idForUpdate).get().getPassword();

        assertThat(passwordAfterUpdate).isNotEqualTo(passwordBeforeUpdate);
    }

    @Test
    void checkUpdateUserShouldReturnUserWithUpdatedRole() {
        UserDto actualUser = service.updateUser(1L, getUpdateDtoWithOnlyUpdatedRole());
        UserDto expectedUser = getUpdatedUserDto();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void checkUpdateUserShouldExistInDbWithUpdatedRole() {
        long idForUpdate = 1L;
        Role roleBeforeUpdate = repository.findById(idForUpdate).get().getRole();
        service.updateUser(idForUpdate, getUpdateDtoWithOnlyUpdatedRole());
        Role roleAfterUpdate = repository.findById(idForUpdate).get().getRole();

        assertThat(roleAfterUpdate).isNotEqualTo(roleBeforeUpdate);
    }

    @Test
    void checkUpdateUserShouldThrowNotFoundException() {
        assertThatThrownBy(() -> service.updateUser(100L, getUpdateDto()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(USER_ID_NOT_FOUND + CANNOT_UPDATE_END);
    }

    @Test
    void checkUpdateUserShouldThrowExceptionEmptyPassword() {
        assertThatThrownBy(() -> service.updateUser(1L, getUpdateDtoWithEmptyPassword()))
                .isInstanceOf(PasswordException.class);
    }

    @Test
    void checkUpdateUserShouldThrowExceptionTooSmallPassword() {
        assertThatThrownBy(() -> service.updateUser(1L, getUpdateDtoWithTooSmallPassword()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkDeleteUserByIdShouldNotExistInDbAfterExecution() {
        long idForDelete = 5L;
        service.deleteUserById(idForDelete);

        assertThat(repository.findById(idForDelete)).isEmpty();
    }

    @Test
    void checkDeleteUserByIdShouldThrowNotFoundException() {
        assertThatThrownBy(() -> service.deleteUserById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(USER_ID_NOT_FOUND + CANNOT_DELETE_END);
    }
}