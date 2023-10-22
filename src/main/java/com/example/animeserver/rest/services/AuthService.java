package com.example.animeserver.rest.services;

import com.example.animeserver.domain.entities.Users;
import com.example.animeserver.domain.repositories.UserRepository;
import com.example.animeserver.rest.dto.UserLogin;
import com.example.animeserver.rest.services.exceptions.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Map<String,String> generateTokens(UserLogin request){
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var user = (Users) authentication.getPrincipal();

        String access_token = jwtService.generateAccessToken(user);
        String refresh_token = jwtService.generateRefreshToken(user);
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        return tokens;
    }

    public String attAccessToken(HttpServletRequest request){
        var email = jwtService.getSubject(request);
        var user = usersRepository.findByEmail(email).get();

        if(jwtService.isTokenValid(request,user)){
            return jwtService.generateAccessToken(user);
        }
        throw new CustomException("invalid token");
    }
}
