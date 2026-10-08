package com.proyecto.backend.dtos;

import lombok.Data;

@Data
public class HospitalDto {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private Boolean activo = true;
}