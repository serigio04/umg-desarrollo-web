package com.proyecto.backend.services;

import com.proyecto.backend.dtos.PacienteDto;
import java.util.List;

public interface PacienteService {
    PacienteDto registrarPaciente(PacienteDto pacienteDto);
    List<PacienteDto> obtenerTodos();
}