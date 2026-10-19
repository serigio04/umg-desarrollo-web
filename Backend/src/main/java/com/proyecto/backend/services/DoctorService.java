package com.proyecto.backend.services;

import com.proyecto.backend.dtos.DoctorDto;
import com.proyecto.backend.entities.Doctor;
import com.proyecto.backend.entities.Hospital;
import com.proyecto.backend.repositories.DoctorRepository;
import com.proyecto.backend.repositories.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    public DoctorService(DoctorRepository doctorRepository, HospitalRepository hospitalRepository) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional(readOnly = true)
    public List<DoctorDto> listarDoctores() {
        return doctorRepository.findByActivoTrue().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DoctorDto crear(DoctorDto dto) {
        // 1. Validar que se envíe el ID del hospital obligatoriamente
        if (dto.getHospitalId() == null) {
            throw new IllegalArgumentException("El campo 'hospitalId' es obligatorio para registrar un doctor.");
        }

        // 2. Buscar el hospital por ID
        Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital no encontrado con ID: " + dto.getHospitalId()));

        // 3. Mapear DTO a la Entidad Doctor
        Doctor doctor = new Doctor();
        doctor.setNombres(dto.getNombres());
        doctor.setApellidos(dto.getApellidos());
        doctor.setEspecialidad(dto.getEspecialidad());
        doctor.setColegiado(dto.getColegiado());
        doctor.setTelefono(dto.getTelefono());
        doctor.setActivo(true);
        doctor.setHospital(hospital);

        // 4. Guardar y retornar el resultado en formato DTO (evita proxies de Hibernate en la respuesta)
        Doctor doctorGuardado = doctorRepository.save(doctor);
        return convertirADto(doctorGuardado);
    }

    private DoctorDto convertirADto(Doctor doctor) {
        DoctorDto dto = new DoctorDto();
        dto.setId(doctor.getId()); // <-- Asignación del ID del doctor
        dto.setNombres(doctor.getNombres());
        dto.setApellidos(doctor.getApellidos());
        dto.setEspecialidad(doctor.getEspecialidad());
        dto.setColegiado(doctor.getColegiado());
        dto.setTelefono(doctor.getTelefono());
        
        if (doctor.getHospital() != null) {
            dto.setHospitalId(doctor.getHospital().getId());
        }
        return dto;
    }
}