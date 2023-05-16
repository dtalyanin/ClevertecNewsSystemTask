package generators.factories;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenFactory {

    private static final String BEARER_PREFIX = "Bearer ";


    public static String getCorrectSubscriberToken(String secretKey, long tokenLifetime) {
        return  Jwts
                .builder()
                .setSubject("User")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getCorrectAdminToken(String secretKey, long tokenLifetime) {
        return  Jwts
                .builder()
                .setSubject("User 4")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getBearerCorrectAdminToken(String secretKey, long tokenLifetime) {
        return BEARER_PREFIX + getCorrectAdminToken(secretKey, tokenLifetime);
    }

    public static String getBearerCorrectSubscriberToken(String secretKey, long tokenLifetime) {
        return BEARER_PREFIX + getCorrectSubscriberToken(secretKey, tokenLifetime);
    }

    public static String getIncorrectToken(String secretKey, long tokenLifetime) {
        return  getCorrectSubscriberToken(secretKey, tokenLifetime) + "123";
    }

    public static String getTokenWithNotExistingUsername(String secretKey, long tokenLifetime) {
        return  Jwts
                .builder()
                .setSubject("NOT EXISTING")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getTokenWithExpiredDate(String secretKey, long tokenLifetime) {
        return  Jwts
                .builder()
                .setSubject("User")
                .setIssuedAt(new Date(System.currentTimeMillis() - tokenLifetime * 2))
                .setExpiration(new Date(System.currentTimeMillis() - tokenLifetime))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
