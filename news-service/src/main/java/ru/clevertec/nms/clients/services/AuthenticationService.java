package ru.clevertec.nms.clients.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.nms.clients.dto.AuthenticationRequest;
import ru.clevertec.nms.clients.dto.AuthenticationResponse;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.security.JwtService;
import ru.clevertec.nms.security.UserDetailsDecorator;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsersService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new NotFoundException("NOT VALID", 1, ErrorCode.NEWS_NOT_FOUND);
        }

        var user = service.getUserByUsername(request.getUsername())
                .orElseThrow();
        System.out.println(user);
        var jwtToken = jwtService.generateToken(new UserDetailsDecorator(user));
        return new AuthenticationResponse(jwtToken);
    }
}
