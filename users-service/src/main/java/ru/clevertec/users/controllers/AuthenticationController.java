package ru.clevertec.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.loggers.annotations.ControllerLog;
import ru.clevertec.users.dto.authentication.AuthenticationDto;
import ru.clevertec.users.dto.authentication.TokenDto;
import ru.clevertec.users.services.AuthenticationService;

/**
 * Controller for performing authentication operations
 */
@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
@ControllerLog
@Tag(name = "Authentication controller", description = "Controller for performing authentication operations")
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Authenticate user by params and return jwt token
     * @param dto request with params for authentication
     * @return token DTO with generated str token
     */
    @Operation(summary = "Authenticate user by params and return jwt token")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = TokenDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE))

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid AuthenticationDto dto) {
        return ResponseEntity.ok(service.authenticate(dto));
    }
}
