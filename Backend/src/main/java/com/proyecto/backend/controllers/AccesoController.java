package com.proyecto.backend.controllers;

import com.proyecto.backend.dtos.MenuDto;
import com.proyecto.backend.services.AccesoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accesos")
@RequiredArgsConstructor
public class AccesoController {

    private final AccesoService accesoService;

    @GetMapping("/menu")
    public ResponseEntity<List<MenuDto>> obtenerMenu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Extraer los roles del token JWT (ej. "ROLE_ADMIN", "ROLE_USER")
        List<String> roles = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        
        System.out.println("Roles extraídos del token JWT: " + roles);

        return ResponseEntity.ok(accesoService.obtenerMenuPorRoles(roles));
    }
}