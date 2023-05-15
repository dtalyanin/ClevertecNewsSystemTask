package ru.clevertec.uas.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.loggers.annotations.ControllerLog;
import ru.clevertec.uas.dto.authentication.AuthenticationDto;
import ru.clevertec.uas.dto.authentication.TokenDto;
import ru.clevertec.uas.services.AuthenticationService;

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
