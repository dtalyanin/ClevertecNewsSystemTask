package ru.clevertec.users.security.services.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.users.security.services.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static ru.clevertec.users.utils.constants.MessageConstants.TOKEN_NOT_VALID;

@Service
public class JwtServiceImpl implements JwtService {

    private final String secretKey;
    private final long tokenLifetime;

    public String getSecretKey() {
        return secretKey;
    }

    public long getTokenLifetime() {
        return tokenLifetime;
    }

    public JwtServiceImpl(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.lifetime}") long tokenLifetime) {
        this.secretKey = secretKey;
        this.tokenLifetime = tokenLifetime;
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(String username) {
        return  Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenException(TOKEN_NOT_VALID, ErrorCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            throw new TokenException(TOKEN_NOT_VALID, ErrorCode.TOKEN_NOT_VALID);
        } catch (JwtException e) {
            throw new TokenException(TOKEN_NOT_VALID, ErrorCode.INCORRECT_TOKEN);
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
