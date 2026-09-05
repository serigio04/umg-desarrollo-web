package com.proyecto.backend.services;

import com.proyecto.backend.dtos.PageResponse;
import com.proyecto.backend.dtos.PacienteDto;
import com.proyecto.backend.dtos.PacienteFilter;
import com.proyecto.backend.entities.Paciente;

public interface PacienteService {
    PageResponse<Paciente> obtenerPacientesPaginados(PacienteFilter filter);
    Paciente crearPaciente(PacienteDto dto);
}