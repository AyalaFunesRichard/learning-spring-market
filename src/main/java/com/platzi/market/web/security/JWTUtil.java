package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final String KEY = "xddddddd";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername()) // biulder permite construir en una secuencia de metodos
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 8640000))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact(); // compact() para crearlo
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        // validar q el tocken no haya vencido
        return getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        // validar q el token sea del usuario
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
