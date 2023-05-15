package ru.clevertec.users.security.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, String username);
    boolean isTokenExpired(String token);
}
