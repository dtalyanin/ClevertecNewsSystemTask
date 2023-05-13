package ru.clevertec.uas.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.clevertec.uas.dto.authentication.AuthenticationDto;
import ru.clevertec.uas.dto.authentication.TokenDto;
import ru.clevertec.uas.exceptions.ErrorCode;
import ru.clevertec.uas.exceptions.AuthenticationException;
import ru.clevertec.uas.security.services.JwtService;
import ru.clevertec.uas.services.AuthenticationService;

import static ru.clevertec.uas.utils.constants.MessageConstants.INCORRECT_AUTH_DATA;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsService detailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override public TokenDto authenticate(AuthenticationDto request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException(INCORRECT_AUTH_DATA, ErrorCode.INCORRECT_AUTHENTICATION_DATA);
        }
        UserDetails userDetails = detailsService.loadUserByUsername(request.username());
        String jwtToken = jwtService.generateToken(userDetails);
        return new TokenDto(jwtToken);
    }
}