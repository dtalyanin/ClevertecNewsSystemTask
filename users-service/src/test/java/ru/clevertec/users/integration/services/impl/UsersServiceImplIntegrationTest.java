package ru.clevertec.users.integration.services.impl;

import generators.factories.UpdateDtoFactory;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.exceptions.UserExistException;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.integration.BaseIntegrationTest;
import ru.clevertec.users.models.responses.ModificationResponse;
import ru.clevertec.users.services.impl.UsersServiceImpl;

import java.util.List;

import static generators.factories.CreateDtoFactory.*;
import static generators.factories.ModificationResponseFactory.*;
import static generators.factories.PageableFactory.*;
import static generators.factories.UpdateDtoFactory.*;
import static generators.factories.UserDtoFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.clevertec.users.utils.constants.MessageConstants.USER_EXIST;
import static ru.clevertec.users.utils.constants.MessageConstants.USER_ID_NOT_FOUND;

class UsersServiceImplIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UsersServiceImpl service;
    @Autowired
    UsersRepository repository;

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
        UserDto expectedUser = getSubscriber();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void checkGetUserByIdShouldThrowNotFoundException() {
        assertThatThrownBy(() -> service.getUserById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(USER_ID_NOT_FOUND);
    }

//    @Test
//    void getUserByToken() {
//    }

    @Test
    void checkAddUserShouldReturnModificationResponseWithGeneratedId() {
        ModificationResponse actualResponse = service.addUser(getCreateDto());
        ModificationResponse expectedResponse = getUserAddedResponse();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void checkAddUserShouldExistInDbAfterExecution() {
        long createdId = service.addUser(getCreateDto()).id();

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
    void checkUpdateUserShouldReturnModificationResponseWithUpdatedId() {
        ModificationResponse actualResponse = service.updateUser(1L, getUpdateDto());
        ModificationResponse expectedResponse = getUserUpdatedResponse();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void checkUpdateUserShouldExistInDbWithUpdatedPasswordAndRole() {
        service.updateUser(1L, getUpdateDto());

    }

    @Test
    void checkDeleteUserByIdShouldReturnModificationResponseWithDeletedId() {
        ModificationResponse actualResponse = service.deleteUserById(5L);
        ModificationResponse expectedResponse = getUserDeletedResponse();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void checkDeleteUserByIdShouldNotExistInDbAfterExecution() {
        long deletedId = service.deleteUserById(5L).id();

        assertThat(repository.findById(deletedId)).isEmpty();
    }
}