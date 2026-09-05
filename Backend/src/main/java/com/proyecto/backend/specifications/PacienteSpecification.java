package com.proyecto.backend.specifications;

import com.proyecto.backend.dtos.PacienteFilter;
import com.proyecto.backend.entities.Paciente;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PacienteSpecification {

    public static Specification<Paciente> conFiltros(PacienteFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getNombre() != null && !filter.getNombre().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase().trim() + "%"));
            }
            if (filter.getApellidos() != null && !filter.getApellidos().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("apellidos")), "%" + filter.getApellidos().toLowerCase().trim() + "%"));
            }
            // if (filter.getDpi() != null && !filter.getDpi().isBlank()) {
            //     predicates.add(cb.equal(root.get("dpi"), filter.getDpi().trim()));
            // }
            if (filter.getEstado() != null) {
                predicates.add(cb.equal(root.get("estado"), filter.getEstado()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}