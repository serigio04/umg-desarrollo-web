package com.proyecto.backend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    // 1. Salta el filtro por completo para peticiones OPTIONS (CORS preflight)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtils.validarToken(token);
                String username = claims.getSubject();

                if (username != null) {
                    // Extraer el claim de roles
                    List<String> roles = claims.get("roles", List.class);
                    
                    // Convertir la lista a SimpleGrantedAuthority
                    List<SimpleGrantedAuthority> authorities = (roles != null) 
                            ? roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()) 
                            : Collections.emptyList();

                    // Inyectar las authorities en el contexto de seguridad
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                            
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}