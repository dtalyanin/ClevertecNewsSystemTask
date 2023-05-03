package ru.clevertec.uas.utils.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.clevertec.uas.dto.CreateDto;
import ru.clevertec.uas.dto.UpdateDto;
import ru.clevertec.uas.dto.UserDto;
import ru.clevertec.uas.dto.UserDtoWithPassword;
import ru.clevertec.uas.models.User;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UsersMapper {

    private static final String PASSWORD_FIELD = "password";
    private static final String ENCODE_PASSWORD_METHOD = "encodePassword";

    @Autowired
    private PasswordEncoder encoder;

    public abstract UserDto convertUserToDto(User user);
    public abstract UserDtoWithPassword convertUserToDtoWithPassword(User user);
    public abstract List<UserDto> convertAllUsersToDtos(Iterable<User> users);
    @Mapping(source = PASSWORD_FIELD, target = PASSWORD_FIELD, qualifiedByName = ENCODE_PASSWORD_METHOD)
    public abstract void updateUser(@MappingTarget User user, UpdateDto dto);
    @Mapping(source = PASSWORD_FIELD, target = PASSWORD_FIELD, qualifiedByName = ENCODE_PASSWORD_METHOD)
    public abstract User convertDtoToUser(CreateDto dto);

    @Named(ENCODE_PASSWORD_METHOD)
    protected String encodePassword(String password) {
        return encoder.encode(password);
    }
}
