package ru.clevertec.users.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.responses.ModificationResponse;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsersWithPagination(Pageable pageable);
    UserDto getUserById(long id);
    UserDto getUserByToken(String token);
    ModificationResponse addUser(CreateDto dto);
    ModificationResponse updateUser(long id, UpdateDto dto);
    ModificationResponse deleteUserById(long id);
}
