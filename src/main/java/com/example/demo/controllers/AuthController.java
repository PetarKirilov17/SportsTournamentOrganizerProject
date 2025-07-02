package com.example.demo.controllers;

import com.example.demo.User;
import com.example.demo.enums.Role;
import com.example.demo.services.AuthService;
import com.example.demo.services.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request.name, request.email, request.password, request.role, request.participantId);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request.email, request.password)
                .map(user -> {
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
    }

    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
        public Role role;
        public Long participantId; // nullable, only for PARTICIPANT
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }
} 