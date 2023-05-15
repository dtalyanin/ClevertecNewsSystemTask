package ru.clevertec.users.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
@ControllerLog
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid AuthenticationDto dto) {
        return ResponseEntity.ok(service.authenticate(dto));
    }
}