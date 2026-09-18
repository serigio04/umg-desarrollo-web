package com.proyecto.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "modulos")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ruta;
    private String icono;
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modulo_padre")
    private Modulo moduloPadre;

    // Colección simple de textos con los nombres de los roles permitidos
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "modulo_roles", joinColumns = @JoinColumn(name = "modulo_id"))
    @Column(name = "rol_nombre")
    private Set<String> rolesPermitidos;
}