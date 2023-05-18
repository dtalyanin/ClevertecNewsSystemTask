package ru.clevertec.users.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsersWithPagination(Pageable pageable);
    UserDto getUserById(long id);
    UserDto getUserByToken(String token);
    UserDto addUser(CreateDto dto);
    UserDto updateUser(long id, UpdateDto dto);
    void deleteUserById(long id);
}
