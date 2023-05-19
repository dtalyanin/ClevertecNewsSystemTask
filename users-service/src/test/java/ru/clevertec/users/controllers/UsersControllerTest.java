package ru.clevertec.users.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.responses.ModificationResponse;
import ru.clevertec.users.services.UsersService;

import java.util.List;

import static generators.factories.CreateDtoFactory.getCreateDto;
import static generators.factories.ModificationResponseFactory.*;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.UpdateDtoFactory.getUpdateDto;
import static generators.factories.UserDtoFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController controller;
    @Mock
    private UsersService service;

    @Test
    void getAllUsersWithPaginationShouldReturn5Users() {
        doReturn(getAllUserDtos()).when(service).getAllUsersWithPagination(any(Pageable.class));

        ResponseEntity<List<UserDto>> actualResponse = controller.getAllUsersWithPagination(getDefaultPageable());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        List<UserDto> actualBody = actualResponse.getBody();

        List<UserDto> expectedBody = getAllUserDtos();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(service).getAllUsersWithPagination(any(Pageable.class));
    }

    @Test
    void checkGetUserByIdShouldReturnExistingUser() {
        doReturn(getAdminDto()).when(service).getUserById(anyLong());

        ResponseEntity<UserDto> actualResponse = controller.getUserById(1L);
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        UserDto actualBody = actualResponse.getBody();

        UserDto expectedBody = getAdminDto();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(service).getUserById(anyLong());
    }

    @Test
    void checkGetUserByTokenShouldReturnUser() {
        doReturn(getAdminDto()).when(service).getUserByToken(anyString());

        ResponseEntity<UserDto> actualResponse = controller.getUserByToken("token");
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        UserDto actualBody = actualResponse.getBody();

        UserDto expectedBody = getAdminDto();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(service).getUserByToken(anyString());
    }

    @Test
    void checkAddUserShouldReturnUserResponseWithGeneratedId() {
        mockStatic(ServletUriComponentsBuilder.class);
        ServletUriComponentsBuilder servletBuilder = mock(ServletUriComponentsBuilder.class);
        when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(servletBuilder);
        doReturn(UriComponentsBuilder.fromPath("")).when(servletBuilder).path(anyString());
        doReturn(getCreatedUserDto()).when(service).addUser(any(CreateDto.class));

        ResponseEntity<ModificationResponse> actualResponse = controller.addUser(getCreateDto());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();
        HttpHeaders actualHeaders = actualResponse.getHeaders();

        ModificationResponse expectedBody = getUserAddedResponse();
        HttpStatus expectedCode = HttpStatus.CREATED;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode),
                () -> assertThat(actualHeaders).containsKey(HttpHeaders.LOCATION));

        verify(service).addUser(any(CreateDto.class));
    }

    @Test
    void checkUpdateUserShouldReturnResponseWithUpdatedId() {
        doReturn(getUpdatedUserDto()).when(service).updateUser(anyLong(), any(UpdateDto.class));

        ResponseEntity<ModificationResponse> actualResponse = controller.updateUser(1L, getUpdateDto());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();

        ModificationResponse expectedBody = getUserUpdatedResponse();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(service).updateUser(anyLong(), any(UpdateDto.class));
    }

    @Test
    void checkDeleteUserByIdShouldReturnResponseWithDeletedId() {
        doNothing().when(service).deleteUserById(anyLong());

        ResponseEntity<ModificationResponse> actualResponse = controller.deleteUserById(5L);
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();

        ModificationResponse expectedBody = getUserDeletedResponse();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(service).deleteUserById(anyLong());
    }
}