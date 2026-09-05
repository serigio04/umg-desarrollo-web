package com.proyecto.backend.dtos;
import lombok.Data;

@Data
public class PacienteFilter {
    private String nombre;
    private String apellidos;
    private Boolean estado;
    private Integer page = 0;
    private Integer size = 50;
}