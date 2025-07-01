package com.WishApp.WishApp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    @Value("${jwt.time.expiration.refresh}")
    private String timeExpirationRefresh;


    //Crear un token

    public String generateToken(UUID userId , String username) {

        return Jwts.builder()
                .setSubject(username)
                .claim("id", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(){

        return toString();
    }


    //validad token de acceso
    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();

            return true;

        }catch (Exception e) {

            return false;

        }
    }

    //Obtener username del token

    public String getUsername(String token) {

        return getClaim(token, Claims::getSubject);
    }

    //Obtener un solo claim

    public <T> T getClaim(String token, Function<Claims,T> clazz) {

        Claims claims = extractAllClaims(token);
        return clazz.apply(claims);
    }



    // Obtener todos los claims del token

    public Claims extractAllClaims(String token){

        return  Jwts.parserBuilder().setSigningKey(getSignatureKey()).
                build().parseClaimsJws(token).getBody();

    }
    // Obtener firma del token
    public Key getSignatureKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
