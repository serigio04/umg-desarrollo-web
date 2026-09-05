package com.proyecto.backend.controllers;

import com.proyecto.backend.dtos.PageResponse;
import com.proyecto.backend.dtos.PacienteDto;
import com.proyecto.backend.dtos.PacienteFilter;
import com.proyecto.backend.entities.Paciente;
import com.proyecto.backend.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<PageResponse<Paciente>> listarPacientes(@ModelAttribute PacienteFilter filter) {
        return ResponseEntity.ok(pacienteService.obtenerPacientesPaginados(filter));
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody PacienteDto pacienteDto) {
        return ResponseEntity.ok(pacienteService.crearPaciente(pacienteDto));
    }
}