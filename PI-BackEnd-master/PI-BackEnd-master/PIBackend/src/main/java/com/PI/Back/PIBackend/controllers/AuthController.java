package com.PI.Back.PIBackend.controllers;

import com.PI.Back.PIBackend.auth.AuthResponse;
import com.PI.Back.PIBackend.auth.Request.LoginRequest;
import com.PI.Back.PIBackend.auth.Request.RegisterRequest;
import com.PI.Back.PIBackend.services.impl.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
