package com.movie.rock.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

// 김승준 - 회원
@Component
public class JwtUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateAccessToken(String memId) {
        return createToken(memId, 1000 * 60 * 15); // 15 minutes
    }

    public String generateRefreshToken(String memId) {
        return createToken(memId, 1000 * 60 * 60 * 24 * 7); // 7 days
    }

    private String createToken(String memId, long expirationTime) {
        return Jwts.builder()
                .setSubject(memId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractMemberId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, String memId) {
        final String extractedMemberId = extractMemberId(token);
        return (extractedMemberId.equals(memId) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
