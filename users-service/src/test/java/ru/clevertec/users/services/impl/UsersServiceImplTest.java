package ru.clevertec.users.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.exceptions.UserExistException;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.User;
import ru.clevertec.users.security.services.JwtService;
import ru.clevertec.users.utils.mappers.UsersMapper;

import java.util.List;
import java.util.Optional;

import static generators.factories.CreateDtoFactory.getCreateDto;
import static generators.factories.CreateDtoFactory.getCreateDtoWithExistedInDbName;
import static generators.factories.PageFactory.getUsersPage;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.UpdateDtoFactory.getUpdateDto;
import static generators.factories.UpdateDtoFactory.getUpdateDtoWithOnlyUpdatedRole;
import static generators.factories.UserDtoFactory.*;
import static generators.factories.UserFactory.getAdmin;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.clevertec.users.utils.constants.MessageConstants.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @InjectMocks
    private UsersServiceImpl service;
    @Mock
    private UsersRepository repository;
    @Mock
    private JwtService jwtService;
    @Mock
    private UsersMapper mapper;

    @Test
    void getAllUsersWithPaginationShouldReturn5Users() {
        doReturn(getUsersPage()).when(repository).findAll(any(Pageable.class));
        doReturn(getAllUserDtos()).when(mapper).convertAllUsersToDtos(anyIterable());

        List<UserDto> actualUsers = service.getAllUsersWithPagination(getDefaultPageable());
        List<UserDto> expectedUsers = getAllUserDtos();

        assertThat(actualUsers).isEqualTo(expectedUsers);

        verify(repository).findAll(any(Pageable.class));
        verify(mapper).convertAllUsersToDtos(anyIterable());
    }

    @Test
    void checkGetUserByIdShouldReturnExistingUser() {
        doReturn(Optional.of(getAdmin())).when(repository).findById(anyLong());
        doReturn(getAdminDto()).when(mapper).convertUserToDto(any(User.class));

        UserDto actualUser = service.getUserById(1L);
        UserDto expectedUser = getAdminDto();

        assertThat(actualUser).isEqualTo(expectedUser);

        verify(repository).findById(anyLong());
        verify(mapper).convertUserToDto(any(User.class));
    }

    @Test
    void checkGetUserByIdShouldThrowNotFoundException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.getUserById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(USER_ID_NOT_FOUND);

        verify(repository).findById(anyLong());
        verify(mapper, never()).convertUserToDto(any(User.class));
    }

    @Test
    void checkGetUserByTokenShouldReturnUser() {
        doReturn("User").when(jwtService).extractUsername(anyString());
        doReturn(Optional.of(getAdmin())).when(repository).findByUsername(anyString());
        doReturn(false).when(jwtService).isTokenExpired(anyString());
        doReturn(getAdminDto()).when(mapper).convertUserToDto(any(User.class));

        String token = "token";
        UserDto actualUser = service.getUserByToken(token);
        UserDto expectedUser = getAdminDto();

        assertThat(actualUser).isEqualTo(expectedUser);

        verify(jwtService).extractUsername(anyString());
        verify(repository).findByUsername(anyString());
        verify(jwtService).isTokenExpired(anyString());
        verify(mapper).convertUserToDto(any(User.class));
    }

    @Test
    void checkGetUserByTokenShouldThrowNotFoundException() {
        doReturn("User").when(jwtService).extractUsername(anyString());
        doReturn(Optional.empty()).when(repository).findByUsername(anyString());

        assertThatThrownBy(() -> service.getUserByToken("token"))
                .isInstanceOf(TokenException.class)
                .hasMessage(TOKEN_NOT_VALID);

        verify(jwtService).extractUsername(anyString());
        verify(repository).findByUsername(anyString());
        verify(jwtService, never()).isTokenExpired(anyString());
        verify(mapper, never()).convertUserToDto(any(User.class));
    }

    @Test
    void checkAddUserShouldReturnUserWithGeneratedId() {
        doReturn(false).when(repository).existsByUsername(anyString());
        doReturn(getAdmin()).when(mapper).convertDtoToUser(any(CreateDto.class));
        doReturn(getAdmin()).when(repository).save(any(User.class));
        doReturn(getCreatedUserDto()).when(mapper).convertUserToDto(any(User.class));

        UserDto actualUser = service.addUser(getCreateDto());
        UserDto expectedUser = getCreatedUserDto();

        assertThat(actualUser).isEqualTo(expectedUser);

        verify(repository).existsByUsername(anyString());
        verify(mapper).convertDtoToUser(any(CreateDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).convertUserToDto(any(User.class));
    }

    @Test
    void checkAddUserShouldThrowExceptionUsernameExist() {
        doReturn(true).when(repository).existsByUsername(anyString());

        assertThatThrownBy(() -> service.addUser(getCreateDtoWithExistedInDbName()))
                .isInstanceOf(UserExistException.class)
                .hasMessage(USER_EXIST);

        verify(repository).existsByUsername(anyString());
        verify(mapper, never()).convertDtoToUser(any(CreateDto.class));
        verify(repository, never()).save(any(User.class));
        verify(mapper, never()).convertUserToDto(any(User.class));
    }

    @Test
    void checkUpdateUserShouldReturnUpdatedUserDto() {
        doReturn(Optional.of(getAdmin())).when(repository).findById(anyLong());
        doNothing().when(mapper).updateUser(any(User.class), any(UpdateDto.class));
        doReturn(getAdmin()).when(repository).save(any(User.class));
        doReturn(getUpdatedUserDto()).when(mapper).convertUserToDto(any(User.class));

        UserDto actualUser = service.updateUser(1L, getUpdateDtoWithOnlyUpdatedRole());
        UserDto expectedUser = getUpdatedUserDto();

        assertThat(actualUser).isEqualTo(expectedUser);

        verify(repository).findById(anyLong());
        verify(mapper).updateUser(any(User.class), any(UpdateDto.class));
        verify(repository).save(any(User.class));
        verify(mapper).convertUserToDto(any(User.class));
    }

    @Test
    void checkUpdateUserShouldThrowNotFoundException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.updateUser(100L, getUpdateDto()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(USER_ID_NOT_FOUND + CANNOT_UPDATE_END);

        verify(repository).findById(anyLong());
        verify(mapper, never()).updateUser(any(User.class), any(UpdateDto.class));
        verify(repository, never()).save(any(User.class));
        verify(mapper, never()).convertUserToDto(any(User.class));
    }

    @Test
    void checkDeleteUserByIdShouldBeCalledWithId() {
        doReturn(1).when(repository).deleteById(anyLong());

        assertThatCode(() -> service.deleteUserById(1L)).doesNotThrowAnyException();

        verify(repository).deleteById(anyLong());
    }

    @Test
    void checkDeleteUserByIdShouldThrowNotFoundException() {
        doReturn(0).when(repository).deleteById(anyLong());

        assertThatThrownBy(() -> service.deleteUserById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(USER_ID_NOT_FOUND + CANNOT_DELETE_END);

        verify(repository).deleteById(anyLong());
    }
}