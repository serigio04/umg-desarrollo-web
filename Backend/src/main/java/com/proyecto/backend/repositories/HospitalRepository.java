package com.proyecto.backend.repositories;

import com.proyecto.backend.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findByActivoTrue();
}