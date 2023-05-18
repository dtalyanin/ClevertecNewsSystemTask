package ru.clevertec.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.loggers.annotations.ControllerLog;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.responses.ModificationResponse;
import ru.clevertec.users.services.UsersService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.users.utils.constants.MessageConstants.*;

/**
 * Controller for performing operations with users entity
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@ControllerLog
@Tag(name = "Users controller", description = "Controller for performing operations with users entity")
public class UsersController {

    private final UsersService service;

    /**
     * Get all existing users and return them according to chosen size and page
     * @param pageable page and maximum size of returning collections
     * @return list of users DTO
     */
    @Operation(summary = "Get all existing users and return them according to chosen size and page")
    @ApiResponse(responseCode = "200", content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE))

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsersWithPagination(Pageable pageable) {
        return ResponseEntity.ok(service.getAllUsersWithPagination(pageable));
    }

    /**
     * Get user with specified ID
     * @param id ID to search
     * @return user DTO with specified ID
     */
    @Operation(summary = "Get user with specified ID")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = UserDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE))

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    /**
     * Get user by jwt token
     * @param token user token
     * @return user DTO that contained in token
     */
    @Operation(summary = "Get user by jwt token")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = UserDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE))

    @GetMapping("/token/{token}")
    public ResponseEntity<UserDto> getUserByToken(@PathVariable @NotBlank(message = EMPTY_TOKEN) String token) {
        return ResponseEntity.ok(service.getUserByToken(token));
    }

    /**
     * Add new user to repository
     * @param dto user DTO to add
     * @return response with created ID
     */
    @Operation(summary = "Add new user to repository")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = ModificationResponse.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE))
    @SecurityRequirement(name = "Bearer Authentication")

    @PostMapping
    public ResponseEntity<ModificationResponse> addUser(@RequestBody @Valid CreateDto dto) {
        UserDto createdDto = service.addUser(dto);
        ModificationResponse response = new ModificationResponse(createdDto.getId(), USER_ADDED);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    /**
     * Update user with specified ID to values that contain DTO
     * @param id ID to update
     * @param dto DTO with values to update
     * @return response with updated ID
     */
    @Operation(summary = "Update user with specified ID to values that contain DTO")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = ModificationResponse.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE))
    @SecurityRequirement(name = "Bearer Authentication")

    @PatchMapping("/{id}")
    public ResponseEntity<ModificationResponse> updateUser(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id, @RequestBody @Valid UpdateDto dto) {
        UserDto updatedDto = service.updateUser(id, dto);
        ModificationResponse response = new ModificationResponse(updatedDto.getId(), USER_UPDATED);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete user with specified ID
     * @param id ID to delete
     * @return response with deleted ID
     */
    @Operation(summary = "Delete user with specified ID")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = ModificationResponse.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE))
    @SecurityRequirement(name = "Bearer Authentication")

    @DeleteMapping("/{id}")
    public ResponseEntity<ModificationResponse> deleteUserById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        service.deleteUserById(id);
        ModificationResponse response = new ModificationResponse(id, USER_DELETED);
        return ResponseEntity.ok(response);
    }
}
