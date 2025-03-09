package com.smarbl.util;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

 // Secret key should be configured in properties file
    @Value("${jwt.secret}")
    private String secretKey; 
    
 // Token expiration time should be configured in properties file
    @Value("${jwt.expiration}")
    private long expirationTime; 

    /**
     * Generate JWT token for a given user email.
     */
    public String generateToken(String email) {
        try {
            logger.info("Generating token for user: {}", email);

            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        } catch (InvalidKeyException e) {
            logger.error("Error generating token: Invalid key", e);
            throw new RuntimeException("Error generating token", e);
        } catch (Exception e) {
            logger.error("Unexpected error while generating token", e);
            throw new RuntimeException("Unexpected error while generating token", e);
        }
    }

    /**
     * Validate the token and check if it's expired.
     */
    public boolean validateToken(String token, String email) {
        return (email.equals(extractEmail(token)) && !isTokenExpired(token));
    }

    /**
     * Extract user email from token.
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract any claim from token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            logger.error("Error extracting claim from token", e);
            throw new RuntimeException("Error extracting claim from token", e);
        }
    }

    /**
     * Check if token is expired.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
