package ru.clevertec.users.security.services;

/**
 * Service for performing operations with jwt token
 */
public interface JwtService {

    /**
     * Get username form token
     * @param token jwt token
     * @return username that token contains
     */
    String extractUsername(String token);

    /**
     * Generate jwt token from username
     * @param username username for generating
     * @return generated jwt token
     */
    String generateToken(String username);

    /**
     * Verify that token contains same name as param and not expired
     * @param token jwt token for check
     * @param username username for check
     * @return true if token contains same name and not expired
     */
    boolean isTokenValid(String token, String username);

    /**
     * Verify that token is expired
     * @param token jwt token for check
     * @return true if token is expired
     */
    boolean isTokenExpired(String token);
}
