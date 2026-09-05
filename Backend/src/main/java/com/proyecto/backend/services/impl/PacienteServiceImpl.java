package com.proyecto.backend.services.impl;

import com.proyecto.backend.dtos.PageResponse;
import com.proyecto.backend.dtos.PacienteDto;
import com.proyecto.backend.dtos.PacienteFilter;
import com.proyecto.backend.dtos.PaginacionMetadata;
import com.proyecto.backend.entities.Paciente;
import com.proyecto.backend.repositories.PacienteRepository;
import com.proyecto.backend.services.PacienteService;
import com.proyecto.backend.specifications.PacienteSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    public PageResponse<Paciente> obtenerPacientesPaginados(PacienteFilter filter) {
        int size = (filter.getSize() != null && (filter.getSize() == 50 || filter.getSize() == 100 || filter.getSize() == 200)) ? filter.getSize() : 50;
        int page = (filter.getPage() != null && filter.getPage() >= 0) ? filter.getPage() : 0;
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

        return new PageResponse<>(pageResult.getContent(), metadata);
    }

    @Override
    public Paciente crearPaciente(PacienteDto dto) {
        Paciente paciente = new Paciente();
        paciente.setNombre(dto.getNombre());
        paciente.setApellidos(dto.getApellidos());
        paciente.setEmail(dto.getEmail());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        return pacienteRepository.save(paciente);
    }
}