package com.proyecto.auth.controllers;

import com.proyecto.auth.entities.Rol;
import com.proyecto.auth.entities.Usuario;
import com.proyecto.auth.repositories.RolRepository;
import com.proyecto.auth.repositories.UsuarioRepository;
import com.proyecto.auth.utils.JwtTokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("El usuario ya existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                .orElseGet(() -> {
                    Rol nuevo = new Rol();
                    nuevo.setNombre("ROLE_USER");
                    return rolRepository.save(nuevo);
                });

        usuario.setRoles(Collections.singleton(rolUser));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente.");
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas.");
        }

        Usuario usuario = userOpt.get();
        List<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());

        String token = tokenProvider.generarToken(usuario.getUsername(), roles);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}

// DTOs auxiliares en el mismo archivo
@Data class RegistroRequest { private String username; private String email; private String password; }
@Data class LoginRequest { private String username; private String password; }