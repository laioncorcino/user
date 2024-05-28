package com.corcino.users.infrastructure.component;

import com.corcino.users.domain.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenComponent {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getName());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setNotBefore(new Date())
                .setExpiration(getExpirationDate())
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Date getExpirationDate() {
        Date currentDate = new Date();
        return new Date(currentDate.getTime() + Long.parseLong(jwtExpiration));
    }

}
