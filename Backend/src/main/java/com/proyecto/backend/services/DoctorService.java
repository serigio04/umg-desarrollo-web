package com.proyecto.backend.services;

import com.proyecto.backend.dtos.DoctorDto;
import com.proyecto.backend.entities.Doctor;
import com.proyecto.backend.entities.Hospital;
import com.proyecto.backend.repositories.DoctorRepository;
import com.proyecto.backend.repositories.HospitalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    public DoctorService(DoctorRepository doctorRepository, HospitalRepository hospitalRepository) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
    }

    public List<Doctor> obtenerActivos() {
        return doctorRepository.findByActivoTrue();
    }

    public Doctor crear(DoctorDto dto) {
        // 1. Buscar el hospital por el ID enviado desde el frontend
        Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital no encontrado con ID: " + dto.getHospitalId()));

        // 2. Mapear el DTO a la Entidad Doctor
        Doctor doctor = new Doctor();
        doctor.setNombres(dto.getNombres());
        doctor.setApellidos(dto.getApellidos());
        doctor.setEspecialidad(dto.getEspecialidad());
        doctor.setColegiado(dto.getColegiado());
        doctor.setTelefono(dto.getTelefono());
        doctor.setActivo(true);
        
        // 3. Asignar el hospital al doctor
        doctor.setHospital(hospital);

        // 4. Guardar en base de datos
        return doctorRepository.save(doctor);
    }
}