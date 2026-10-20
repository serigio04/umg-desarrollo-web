package com.proyecto.backend.controllers;

import com.proyecto.backend.dtos.CitaDto;
import com.proyecto.backend.services.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERADOR')")
    public ResponseEntity<List<CitaDto>> listarCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CitaDto> crearCita(@RequestBody CitaDto dto) {
        return ResponseEntity.ok(citaService.crearCita(dto));
    }
}