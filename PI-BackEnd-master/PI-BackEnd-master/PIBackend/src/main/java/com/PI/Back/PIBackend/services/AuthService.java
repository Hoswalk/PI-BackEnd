package com.PI.Back.PIBackend.services;

import com.PI.Back.PIBackend.auth.AuthResponse;
import com.PI.Back.PIBackend.auth.Request.LoginRequest;
import com.PI.Back.PIBackend.auth.Request.RegisterRequest;
import com.PI.Back.PIBackend.entity.Role;
import com.PI.Back.PIBackend.entity.Usuario;
import com.PI.Back.PIBackend.jwt.JwtService;
import com.PI.Back.PIBackend.repository.UsuarioRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Validator validator;

    public AuthResponse register(@Valid RegisterRequest request) {
        //Validando el DTO
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()){
            //Manejar las violaciones segun necesidades
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterRequest> violation : violations){
                sb.append(violation.getMessage()).append("ERROR");
            }
            throw new IllegalArgumentException(sb.toString());
        }


        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .role(Role.USUARIO)
                .password(passwordEncoder.encode(request.getPassword()))  // Encriptar la contraseña
                .build();

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = usuarioRepository.findUsuarioByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
