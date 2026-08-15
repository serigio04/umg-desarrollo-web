package com.proyecto.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data // Lombok genera Getters, Setters, toString, etc.
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
}
