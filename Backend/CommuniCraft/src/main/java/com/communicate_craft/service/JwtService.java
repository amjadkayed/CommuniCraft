package com.communicate_craft.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    // or in application properties
    private static final String SECRET_KEY = "3e5049d3a1415694905d238eda5a9c62eb034f3e220031793ca2395b8166a037";
    // a signing key is a secret that is used to digitally sign the JWT
    // used to create the signature part of the JWT which is used to verify
    // that the sender of the JWT is who it claims to be, and ensure that
    // the message wasn't changed along the way
    // key size must be at least 250-bits for security reasons
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
