package com.gizasystems.apigateway.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY ;
    private SecretKey key;

    @PostConstruct
    private void initializeKey() {
        // Decode the SECRET_KEY after it is injected
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public boolean validateToken(String token) {
        return extractId(token) != null && !isTokenExpired(token);
    }



    public Long extractId(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("id", Long.class);
        } catch (JwtException e) {
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            String role = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("role", String.class);

            return role != null ? role.toUpperCase() : null;
        } catch (JwtException e) {
            return null;
        }
    }

    private Boolean isTokenExpired(String token) {
        return Objects.requireNonNull(extractExpiration(token)).before(new Date());
    }


    private Date extractExpiration(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
        } catch (JwtException e) {
            return null;
        }
    }

}
