package com.example.animeserver.rest.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.animeserver.domain.entities.Users;
import com.example.animeserver.rest.services.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt_secret_password}")
    private String jwtSecret;

    @Value("${jwt_data_expiration_access_token}")
    private int expiresAccessToken;

    @Value("${jwt_data_expiration_refresh_token}")
    private int expiresRefreshToken;

    public String generateAccessToken(Users users){
        return JWT.create().withSubject(users.getUsername())
                .withClaim("id", users.getId())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiresAccessToken))
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    public String generateRefreshToken(Users users){
        return JWT.create().withSubject(users.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiresRefreshToken))
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    public Long getId(HttpServletRequest request){
        var token = getToken(request);
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getClaim("id").asLong();
    }

    public String getSubject(HttpServletRequest request){
        var token = getToken(request);
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getSubject();
    }

    public Date getExpiresAt(HttpServletRequest request){
        var token = getToken(request);
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getExpiresAt();
    }

    public boolean isTokenValid(HttpServletRequest request, Users users){
        var token = getToken(request);
        String username = getSubject(request);
        return username.equals(users.getUsername()) && !isTokenExpired(request);
    }

    private boolean isTokenExpired(HttpServletRequest request){
        Date expirationDate = getExpiresAt(request);
        return expirationDate.before(new Date());
    }

    public String getToken(HttpServletRequest request){
        var auth = request.getHeader("Authorization");
        if(auth == null){
            throw new AuthenticationException("User is not authorized");
        }
        var token = auth.replace("Bearer ", "");
        return token;
    }

}
