package ru.clevertec.users.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.clevertec.exceptions.exceptions.AuthenticationException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.users.dto.authentication.AuthenticationDto;
import ru.clevertec.users.dto.authentication.TokenDto;
import ru.clevertec.users.security.services.JwtService;
import ru.clevertec.users.services.AuthenticationService;

import static ru.clevertec.users.utils.constants.MessageConstants.INCORRECT_AUTH_DATA;

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
