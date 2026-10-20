package com.proyecto.backend.services;

import com.proyecto.backend.dtos.CitaDto;
import com.proyecto.backend.entities.Cita;
import com.proyecto.backend.entities.Doctor;
import com.proyecto.backend.entities.Hospital;
import com.proyecto.backend.entities.Paciente;
import com.proyecto.backend.repositories.CitaRepository;
import com.proyecto.backend.repositories.DoctorRepository;
import com.proyecto.backend.repositories.HospitalRepository;
import com.proyecto.backend.repositories.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    public CitaService(CitaRepository citaRepository,
                       PacienteRepository pacienteRepository,
                       DoctorRepository doctorRepository,
                       HospitalRepository hospitalRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional(readOnly = true)
    public List<CitaDto> listarCitas() {
        return citaRepository.findByActivoTrue().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CitaDto crearCita(CitaDto dto) {
        // Validaciones de presencia de IDs obligatorios
        if (dto.getPacienteId() == null) {
            throw new IllegalArgumentException("El campo 'pacienteId' es obligatorio.");
        }
        if (dto.getDoctorId() == null) {
            throw new IllegalArgumentException("El campo 'doctorId' es obligatorio.");
        }
        if (dto.getHospitalId() == null) {
            throw new IllegalArgumentException("El campo 'hospitalId' es obligatorio.");
        }
        if (dto.getFechaHora() == null) {
            throw new IllegalArgumentException("El campo 'fechaHora' es obligatorio.");
        }

        // Buscar entidades en base de datos
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + dto.getDoctorId()));

        Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital no encontrado con ID: " + dto.getHospitalId()));

        // Mapear y guardar Cita
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
        cita.setHospital(hospital);
        cita.setFechaHora(dto.getFechaHora());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(dto.getEstado() != null ? dto.getEstado() : "PENDIENTE");
        cita.setActivo(true);

        Cita citaGuardada = citaRepository.save(cita);
        return convertirADto(citaGuardada);
    }

    private CitaDto convertirADto(Cita cita) {
        CitaDto dto = new CitaDto();
        dto.setId(cita.getId());
        dto.setFechaHora(cita.getFechaHora());
        dto.setMotivo(cita.getMotivo());
        dto.setEstado(cita.getEstado());

        if (cita.getPaciente() != null) {
            dto.setPacienteId(cita.getPaciente().getId());
            dto.setNombrePaciente(cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellidos());
        }

        if (cita.getDoctor() != null) {
            dto.setDoctorId(cita.getDoctor().getId());
            dto.setNombreDoctor(cita.getDoctor().getNombres() + " " + cita.getDoctor().getApellidos());
        }

        if (cita.getHospital() != null) {
            dto.setHospitalId(cita.getHospital().getId());
            dto.setNombreHospital(cita.getHospital().getNombre());
        }

        return dto;
    }
}