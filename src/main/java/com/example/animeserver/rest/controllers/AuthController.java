package com.example.animeserver.rest.controllers;

import com.example.animeserver.rest.dto.UserLogin;
import com.example.animeserver.rest.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "perform user authentication with email and password returning a Map with access and update tokens")
    public ResponseEntity<Map<String,String>> login(@RequestBody @Valid UserLogin request){
        return ResponseEntity.ok(authService.generateTokens(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "updates access token via refresh token")
    public ResponseEntity<String> attAccessToken(HttpServletRequest request){
        return ResponseEntity.ok(authService.attAccessToken(request));
    }
}
