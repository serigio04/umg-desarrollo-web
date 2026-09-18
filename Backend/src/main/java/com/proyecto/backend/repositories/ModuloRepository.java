package com.proyecto.backend.repositories;

import com.proyecto.backend.entities.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    
    @Query("SELECT DISTINCT m FROM Modulo m JOIN m.rolesPermitidos r WHERE r IN :roles AND m.activo = true")
    List<Modulo> findByRolesIn(@Param("roles") List<String> roles);
}