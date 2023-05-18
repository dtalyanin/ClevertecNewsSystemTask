package ru.clevertec.users.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;

import java.util.List;

/**
 * Service for performing operations with users entity
 */
public interface UsersService {

    /**
     * Get all existing users and return them according to chosen size and page
     * @param pageable page and maximum size of returning collections
     * @return list of users DTO
     */
    List<UserDto> getAllUsersWithPagination(Pageable pageable);

    /**
     * Get user with specified ID
     * @param id ID to search
     * @return user DTO with specified ID
     */
    UserDto getUserById(long id);

    /**
     * Get user by token
     * @param token user token
     * @return user DTO that contained in token
     */
    UserDto getUserByToken(String token);

    /**
     * Add new user to repository
     * @param dto user DTO to add
     * @return created user DTO
     */
    UserDto addUser(CreateDto dto);

    /**
     * Update user with specified ID to values that contain DTO
     * @param id ID to update
     * @param dto DTO with values to update
     * @return updated user DTO
     */
    UserDto updateUser(long id, UpdateDto dto);

    /**
     * Delete user with specified ID
     * @param id ID to delete
     */
    void deleteUserById(long id);
}
