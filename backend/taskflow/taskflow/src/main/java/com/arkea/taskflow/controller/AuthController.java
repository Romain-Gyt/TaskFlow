package com.arkea.taskflow.controller;

import com.arkea.taskflow.dto.LoginRequest;
import com.arkea.taskflow.service.AuthService;
import com.arkea.taskflow.utils.CookieUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.loginAndGenerateToken(loginRequest.email(), loginRequest.password());

        ResponseCookie cookie = CookieUtils.addAuthCookie(token);
        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(Map.of("message", "Authentification réussie"));
    }
}