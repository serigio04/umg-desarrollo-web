package com.proyecto.backend.controllers;

import com.proyecto.backend.dtos.DoctorDto;
import com.proyecto.backend.services.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERADOR')")
    public ResponseEntity<List<DoctorDto>> listarDoctores() {
        return ResponseEntity.ok(doctorService.listarDoctores());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DoctorDto> crearDoctor(@RequestBody DoctorDto dto) {
        return ResponseEntity.ok(doctorService.crear(dto));
    }
}