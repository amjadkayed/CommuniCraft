package com.communicate_craft.service_implementation;

import com.communicate_craft.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    //---------------------------- extract from token

    // or in application properties
    private static final String SECRET_KEY = "3e5049d3a1415694905d238eda5a9c62eb034f3e220031793ca2395b8166a037";

    // a signing key is a secret that is used to digitally sign the JWT
    // used to create the signature part of the JWT which is used to verify
    // that the sender of the JWT is who it claims to be, and ensure that
    // the message wasn't changed along the way
    // key size must be at least 250-bits for security reasons
    private Key getSignInKey() {
        log.info("JwtService --> getSignInKey");
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        log.info("JwtService --> extractAllClaims");
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        log.info("JwtService --> extractClaim with claimResolver: " + claimResolver);
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        log.info("JwtService --> extractUsername");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        log.info("JwtService --> extractExpiration");
        return extractClaim(token, Claims::getExpiration);
    }
    //---------------------------- generate token

    public String generateToken(User userDetails) {
        log.info("JwtService --> generateToken without claims");
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        log.info("JwtService --> generateToken with claims");
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //---------------------------- check token's validation
    public boolean isTokenExpired(String token) {
        log.info("JwtService --> isTokenExpired");
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, User userDetails) {
        log.info("JwtService --> isTokenValid");
        String username = extractUsername(token);
        return (username.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }
}
