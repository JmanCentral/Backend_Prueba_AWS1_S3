package com.WishApp.WishApp.config;

import com.WishApp.WishApp.entities.User;
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
    private long timeExpiration;

    @Value("${jwt.time.refresh}")
    private long timeExpirationRefresh;

    //Crear un token

    public String generateToken(User user , long expireTime) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = getUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(getSignatureKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }


    public String generateAccessToken (User user) {

        return generateToken(user,timeExpiration);
    }

    public String generateRefreshToken(User user){

        return generateToken(user , timeExpirationRefresh);
    }

    //validar token de acceso
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
