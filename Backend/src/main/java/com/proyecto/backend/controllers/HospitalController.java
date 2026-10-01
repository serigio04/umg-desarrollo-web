package com.proyecto.backend.controllers;

import com.proyecto.backend.entities.Hospital;
import com.proyecto.backend.services.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitales")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERADOR')")
    public ResponseEntity<List<Hospital>> listarHospitales() {
        return ResponseEntity.ok(hospitalService.obtenerActivos());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Hospital> crearHospital(@RequestBody Hospital hospital) {
        return ResponseEntity.ok(hospitalService.crear(hospital));
    }
}