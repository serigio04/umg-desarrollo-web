package com.proyecto.backend.dtos;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuDto {
    private Long id;
    private String nombre;
    private String ruta;
    private String icono;
    private Long idModuloPadre;
    private List<MenuDto> subModulos = new ArrayList<>();

    public MenuDto(Long id, String nombre, String ruta, String icono, Long idModuloPadre) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.icono = icono;
        this.idModuloPadre = idModuloPadre;
    }
}