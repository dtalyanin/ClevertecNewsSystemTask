package ru.clevertec.nms.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.nms.clients.dto.AuthenticationRequest;
import ru.clevertec.nms.clients.dto.AuthenticationResponse;
import ru.clevertec.nms.clients.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authDto) {
        System.out.println(authDto);
        AuthenticationResponse response = service.authenticate(authDto);
        System.out.println(response.token());
        return ResponseEntity.ok(response);
    }
}
