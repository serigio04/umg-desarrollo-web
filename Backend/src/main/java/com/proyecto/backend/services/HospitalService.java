package com.proyecto.backend.services;

import com.proyecto.backend.entities.Hospital;
import com.proyecto.backend.repositories.HospitalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> obtenerActivos() {
        return hospitalRepository.findByActivoTrue();
    }

    public Hospital crear(Hospital hospital) {
        hospital.setActivo(true);
        return hospitalRepository.save(hospital);
    }
}