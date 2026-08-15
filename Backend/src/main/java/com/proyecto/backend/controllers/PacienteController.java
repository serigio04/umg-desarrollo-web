package com.proyecto.backend.controllers;

import com.proyecto.backend.dtos.PacienteDto;
import com.proyecto.backend.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @PostMapping
    public ResponseEntity<PacienteDto> registrar(@RequestBody PacienteDto dto) {
        return new ResponseEntity<>(service.registrarPaciente(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PacienteDto>> listar() {
        return new ResponseEntity<>(service.obtenerTodos(), HttpStatus.OK);
    }
}