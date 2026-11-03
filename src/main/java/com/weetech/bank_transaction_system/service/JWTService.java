
package com.weetech.bank_transaction_system.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    private final String secretKey =
            "RWqN1RbRkPNlUHlXtkLs/9rN/1mSukrUpDHg0e92s3s";

    // Generate JWT Token
    public String generateToken(String email) {

        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(currentTime))
                .expiration(new Date(currentTime + 30 * 60 * 1000L))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    // Get Signing Key
    private SecretKey getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract Email
    public String extractEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // Extract Specific Claim
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // Validate Token
    public boolean validateToken(
            String token,
            UserDetails userDetails) {

        final String username = extractEmail(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    // Check Expiration
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    // Extract Expiration
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    // Verify JWT Signature and Extract Claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
