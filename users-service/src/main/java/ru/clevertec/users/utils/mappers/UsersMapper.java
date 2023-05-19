package ru.clevertec.users.utils.mappers;

import jakarta.validation.Valid;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import ru.clevertec.exceptions.exceptions.PasswordException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.User;

import java.util.List;

import static ru.clevertec.users.utils.constants.MessageConstants.EMPTY_PASSWORD;

/**
 * Mapper for converting users
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Validated
public abstract class UsersMapper {

    private static final String PASSWORD_FIELD = "password";
    private static final String ENCODE_PASSWORD_METHOD = "encodePassword";

    @Autowired
    private PasswordEncoder encoder;

    public abstract UserDto convertUserToDto(User user);
    public abstract List<UserDto> convertAllUsersToDtos(Iterable<User> users);
    @Mapping(source = PASSWORD_FIELD, target = PASSWORD_FIELD, qualifiedByName = ENCODE_PASSWORD_METHOD)
    public abstract void updateUser(@MappingTarget User user, @Valid UpdateDto dto);
    @Mapping(source = PASSWORD_FIELD, target = PASSWORD_FIELD, qualifiedByName = ENCODE_PASSWORD_METHOD)
    public abstract User convertDtoToUser(@Valid CreateDto dto);

    @Named(ENCODE_PASSWORD_METHOD)
    protected String encodePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new PasswordException(EMPTY_PASSWORD, password, ErrorCode.INCORRECT_FIELD_VALUE);
        }
        return encoder.encode(password);
    }
}
