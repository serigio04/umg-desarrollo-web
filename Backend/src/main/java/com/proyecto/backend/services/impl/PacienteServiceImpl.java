package com.proyecto.backend.services.impl;

import com.proyecto.backend.dtos.PacienteDto;
import com.proyecto.backend.entities.Paciente;
import com.proyecto.backend.repositories.PacienteRepository;
import com.proyecto.backend.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;

    @Override
    public PacienteDto registrarPaciente(PacienteDto dto) {
        // Mapeo manual DTO -> Entidad
        Paciente paciente = new Paciente();
        paciente.setNombre(dto.getNombre());
        paciente.setApellidos(dto.getApellidos());
        paciente.setEmail(dto.getEmail());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());

        // Guardar en BD
        Paciente guardado = repository.save(paciente);

        // Mapeo Entidad -> DTO para retornar
        dto.setId(guardado.getId());
        return dto;
    }

    @Override
    public List<PacienteDto> obtenerTodos() {
        return repository.findAll().stream().map(paciente -> {
            PacienteDto dto = new PacienteDto();
            dto.setId(paciente.getId());
            dto.setNombre(paciente.getNombre());
            dto.setApellidos(paciente.getApellidos());
            dto.setEmail(paciente.getEmail());
            dto.setFechaNacimiento(paciente.getFechaNacimiento());
            return dto;
        }).collect(Collectors.toList());
    }
}