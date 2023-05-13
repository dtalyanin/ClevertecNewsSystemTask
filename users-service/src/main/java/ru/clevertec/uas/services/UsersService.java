package ru.clevertec.uas.services;

import ru.clevertec.uas.dto.CreateDto;
import ru.clevertec.uas.dto.UpdateDto;
import ru.clevertec.uas.dto.UserDto;
import ru.clevertec.uas.models.responses.ModificationResponse;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers();
    UserDto getUserById(long id);
    UserDto getUserByToken(String token);
    ModificationResponse addUser(CreateDto dto);
    ModificationResponse updateUser(long id, UpdateDto dto);
    ModificationResponse deleteUserById(long id);
}
