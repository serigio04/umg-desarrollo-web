package com.proyecto.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hospitales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String direccion;
    private String telefono;
    private Boolean activo = true;
}