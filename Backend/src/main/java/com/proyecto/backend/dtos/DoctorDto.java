package com.proyecto.backend.dtos;

import lombok.Data;

@Data
public class DoctorDto {
    private Long id;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private String colegiado;
    private String telefono;
    private Long hospitalId; 
}