package ru.clevertec.users.security.services;

public interface JwtService {

    String extractUsername(String token);
    String generateToken(String username);
    boolean isTokenValid(String token, String username);
    boolean isTokenExpired(String token);
}
