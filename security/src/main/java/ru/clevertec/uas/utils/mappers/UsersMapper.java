package ru.clevertec.uas.utils.mappers;

import org.mapstruct.Mapper;
import ru.clevertec.uas.dto.UserDto;
import ru.clevertec.uas.models.User;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    public UserDto convertUserToDto(User user);
}
