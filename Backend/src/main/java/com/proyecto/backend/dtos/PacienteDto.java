package com.proyecto.backend.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteDto {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fechaNacimiento;
    private String identificacion;
    private String direccion;
    private String telefono;
    private Boolean estado;
}