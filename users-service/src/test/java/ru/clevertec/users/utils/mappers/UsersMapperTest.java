package ru.clevertec.users.utils.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.clevertec.exceptions.exceptions.PasswordException;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.User;

import java.util.List;

import static generators.factories.CreateDtoFactory.getCreateDto;
import static generators.factories.UpdateDtoFactory.getUpdateDto;
import static generators.factories.UserDtoFactory.getAllUserDtos;
import static generators.factories.UserDtoFactory.getSubscriberDto;
import static generators.factories.UserFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static ru.clevertec.users.utils.constants.MessageConstants.EMPTY_PASSWORD;

@ExtendWith(MockitoExtension.class)
class UsersMapperTest {

    @InjectMocks
    private UsersMapper mapper = Mappers.getMapper(UsersMapper.class);
    @Mock
    private PasswordEncoder encoder;

    private final String encodedPassword = "$2a$12$kdKrgZgEEmQSzgWiGs.6AOErkFP1tXvxJ.4gtnpQAVZyNmFC7cZn2";

    @Test
    void checkConvertUserToDtoShouldReturnSubscriberDto() {
        UserDto actualDto = mapper.convertUserToDto(getSubscriber());
        UserDto expectedDto = getSubscriberDto();

        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void checkConvertAllUsersToDtos() {
        List<UserDto> actualDtos = mapper.convertAllUsersToDtos(getAllUsers());
        List<UserDto> expectedDtos = getAllUserDtos();

        assertThat(actualDtos).isEqualTo(expectedDtos);
    }

    @Test
    void checkUpdateUserShouldUpdateRoleAndPassword() {
        doReturn(encodedPassword).when(encoder).encode(anyString());
        User updatedUser = getAdmin();
        mapper.updateUser(updatedUser, getUpdateDto());

        assertThat(updatedUser).isEqualTo(getUpdatedUser());

        verify(encoder).encode(anyString());
    }

    @Test
    void checkConvertDtoToUserShouldReturnCreatedUserWithoutId() {
        doReturn(encodedPassword).when(encoder).encode(anyString());

        User actualUser = mapper.convertDtoToUser(getCreateDto());
        User expectedUser = getCreatedUser();

        assertThat(actualUser).isEqualTo(expectedUser);

        verify(encoder).encode(anyString());
    }

    @Test
    void checkEncodePasswordShouldReturnNewPassword() {
        doReturn(encodedPassword).when(encoder).encode(anyString());

        String actual = mapper.encodePassword("password");

        assertThat(actual).isEqualTo(encodedPassword);
    }

    @Test
    void checkEncodePasswordShouldThrowNullPasswordException() {
        assertThatThrownBy(() -> mapper.encodePassword(null))
                .isInstanceOf(PasswordException.class)
                .hasMessage(EMPTY_PASSWORD);
    }

    @Test
    void checkEncodePasswordShouldThrowBlankPasswordException() {
        assertThatThrownBy(() -> mapper.encodePassword("   "))
                .isInstanceOf(PasswordException.class)
                .hasMessage(EMPTY_PASSWORD);
    }
}