package com.proyecto.backend.services;

import com.proyecto.backend.dtos.MenuDto;
import com.proyecto.backend.entities.Modulo;
import com.proyecto.backend.repositories.ModuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccesoService {

    private final ModuloRepository moduloRepository;

    public List<MenuDto> obtenerMenuPorRoles(List<String> rolesUsuario) {
        // 1. Si el usuario no tiene roles, retornamos lista vacía
        if (rolesUsuario == null || rolesUsuario.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. Obtener módulos permitidos para los roles del JWT
        List<Modulo> modulosPermitidos = moduloRepository.findByRolesIn(rolesUsuario);

        // 3. Convertir a DTOs y mapear por ID para armar la jerarquía
        Map<Long, MenuDto> mapaModulos = new HashMap<>();
        for (Modulo m : modulosPermitidos) {
            Long idPadre = (m.getModuloPadre() != null) ? m.getModuloPadre().getId() : null;
            mapaModulos.put(m.getId(), new MenuDto(m.getId(), m.getNombre(), m.getRuta(), m.getIcono(), idPadre));
        }

        // 4. Construir la jerarquía Padre-Hijo
        List<MenuDto> menuFinal = new ArrayList<>();
        
        for (MenuDto dto : mapaModulos.values()) {
            if (dto.getIdModuloPadre() == null) {
                // Es un módulo principal
                menuFinal.add(dto);
            } else {
                // Es un submódulo, asignarlo a su padre si el padre también está permitido
                MenuDto padre = mapaModulos.get(dto.getIdModuloPadre());
                if (padre != null) {
                    padre.getSubModulos().add(dto);
                }
            }
        }
        
        return menuFinal;
    }
}