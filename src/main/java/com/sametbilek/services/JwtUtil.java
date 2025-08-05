package com.sametbilek.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException; // Doğru exception türü
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long TOKEN_VALIDITY = 3600000;

    public String generateToken(String username, List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .sign(algorithm);
    }

    // Değişiklik burada
    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return  JWT.require(algorithm).build().verify(token);
    }

    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
    public List<String> getRolesFromToken(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return decodedJWT.getClaim("roles").asList(String.class);
        } catch (JWTVerificationException e) {
            return Collections.emptyList();
        }
    }
}