package com.proyecto.backend.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitaDto {
    private Long id;
    private Long pacienteId;
    private String nombrePaciente;
    private Long doctorId;
    private String nombreDoctor;
    private Long hospitalId;
    private String nombreHospital;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
}