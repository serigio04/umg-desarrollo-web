package com.proyecto.auth.controllers;

import com.proyecto.auth.entities.Rol;
import com.proyecto.auth.entities.Usuario;
import com.proyecto.auth.repositories.RolRepository;
import com.proyecto.auth.repositories.UsuarioRepository;
import com.proyecto.auth.utils.JwtTokenProvider;

// Imports para Bucket4j y HTTP
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    // Caché en memoria para almacenar los buckets por IP
    private final Map<String, Bucket> rateLimitCache = new ConcurrentHashMap<>();

    // Regla: 10 peticiones por minuto
    private Bucket resolverBucket(String ip) {
        return rateLimitCache.computeIfAbsent(ip, k -> Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) throws Exception {
        
        // 1. Rate Limiting por IP
        String clientIp = httpRequest.getRemoteAddr();
        Bucket bucket = resolverBucket(clientIp);
        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("HTTP 429: Demasiadas solicitudes.");
        }

        // Buscar al usuario
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(request.getUsername());
        
        // Si no existe, devolvemos error genérico
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        }

        Usuario usuario = userOpt.get();

        // 3. Verificar Bloqueo Temporal
        if (usuario.getCuentaBloqueadaHasta() != null && usuario.getCuentaBloqueadaHasta().isAfter(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas."); 
        }

        // 2. Verificar Contraseña (Intentos Fallidos)
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);
            
            if (usuario.getIntentosFallidos() >= 5) {
                // Bloquear por 15 minutos
                usuario.setCuentaBloqueadaHasta(LocalDateTime.now().plusMinutes(15));
            }
            usuarioRepository.save(usuario); // Guardar el fallo en BD
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        }

        // Si la contraseña es correcta: Resetear contadores
        usuario.setIntentosFallidos(0);
        usuario.setCuentaBloqueadaHasta(null);
        usuarioRepository.save(usuario);

        // Generar Token (Tu código original)
        List<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());

        String token = tokenProvider.generarToken(usuario.getUsername(), roles);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("El usuario ya existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        String nombreRol = (request.getRol() != null && !request.getRol().isBlank()) 
                ? request.getRol().toUpperCase() 
                : "ROLE_USER";

        if (!nombreRol.startsWith("ROLE_")) {
            nombreRol = "ROLE_" + nombreRol;
        }

        final String rolFinal = nombreRol;
        Rol rolAsignado = rolRepository.findByNombre(rolFinal)
                .orElseGet(() -> {
                    Rol nuevo = new Rol();
                    nuevo.setNombre(rolFinal);
                    return rolRepository.save(nuevo);
                });

        usuario.setRoles(Collections.singleton(rolAsignado));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente con el rol: " + rolFinal);
    }
}

// DTOs auxiliares
@Data 
class RegistroRequest { 
    private String username; 
    private String email; 
    private String password; 
    private String rol;
}

@Data 
class LoginRequest { 
    private String username; 
    private String password; 
}