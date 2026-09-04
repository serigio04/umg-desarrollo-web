package com.proyecto.backend.controllers;

import com.proyecto.backend.dtos.PageResponse;
import com.proyecto.backend.dtos.PaginacionMetadata;
import com.proyecto.backend.dtos.PacienteFilter;
import com.proyecto.backend.entities.Paciente;
import com.proyecto.backend.repositories.PacienteRepository;
import com.proyecto.backend.specifications.PacienteSpecification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    @GetMapping
    public ResponseEntity<PageResponse<Paciente>> listarPacientes(@ModelAttribute PacienteFilter filter) {
        // Validar y restringir tamaños de página permitidos
        int size = (filter.getSize() != null && (filter.getSize() == 50 || filter.getSize() == 100 || filter.getSize() == 200))
                ? filter.getSize() 
                : 50;
        
        int page = (filter.getPage() != null && filter.getPage() >= 0) ? filter.getPage() : 0;

        // Ordenamiento id DESC (del más reciente al más antiguo)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<Paciente> pageResult = pacienteRepository.findAll(PacienteSpecification.conFiltros(filter), pageable);

        PaginacionMetadata metadata = PaginacionMetadata.builder()
                .totalRecords(pageResult.getTotalElements())
                .page(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalPages(pageResult.getTotalPages())
                .hasPreviousPage(pageResult.hasPrevious())
                .hasNextPage(pageResult.hasNext())
                .build();

        return ResponseEntity.ok(new PageResponse<>(pageResult.getContent(), metadata));
    }
}