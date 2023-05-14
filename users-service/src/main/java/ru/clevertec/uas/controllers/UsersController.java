package ru.clevertec.uas.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.uas.dto.CreateDto;
import ru.clevertec.uas.dto.UpdateDto;
import ru.clevertec.uas.dto.UserDto;
import ru.clevertec.uas.models.responses.ModificationResponse;
import ru.clevertec.uas.services.UsersService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.uas.utils.constants.MessageConstants.EMPTY_TOKEN;
import static ru.clevertec.uas.utils.constants.MessageConstants.MIN_ID_MESSAGE;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UsersController {

    private final UsersService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<UserDto> getUserByToken(@PathVariable @NotBlank(message = EMPTY_TOKEN) String token) {
        return ResponseEntity.ok(service.getUserByToken(token));
    }

    @PostMapping
    public ResponseEntity<ModificationResponse> addUser(@RequestBody @Valid CreateDto dto) {
        ModificationResponse response = service.addUser(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModificationResponse> updateUser(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id, @RequestBody @Valid UpdateDto dto) {
        return ResponseEntity.ok(service.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ModificationResponse> deleteUserById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(service.deleteUserById(id));
    }

}
