package com.nextTasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nextTasks.DTO.AuthResponseDTO;
import com.nextTasks.DTO.LoginRequestDTO;
import com.nextTasks.DTO.RegisterRequestDTO;
import com.nextTasks.model.User;
import com.nextTasks.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);

        return new AuthResponseDTO(
                jwtToken,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFullName(),
                "USER"
        );
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponseDTO(
                jwtToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                "USER"
        );
    }

    public AuthResponseDTO checkTokenStatus(String token) {
        // Verificar si el token está presente
        if (token == null || token.trim().isEmpty()) {
            throw new RuntimeException("No token provided");
        }

        String cleanToken = token.trim();
        
        // Extraer información del token
        String username;
        try {
            username = jwtService.extractUsername(cleanToken);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token format");
        }
        
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Invalid token - no username found");
        }

        // Buscar el usuario directamente en la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Validar que el token sea válido para este usuario
        try {
            if (!jwtService.isTokenValid(cleanToken, user)) {
                throw new RuntimeException("Token is invalid or expired");
            }
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed");
        }

        // Generar un nuevo token (opcional - para renovar la sesión)
        String newToken = jwtService.generateToken(user);
        
        // Crear la respuesta completa con toda la información del usuario
        return new AuthResponseDTO(
            newToken,
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            "USER"
        );
    }
}
