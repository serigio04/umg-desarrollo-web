package com.proyecto.backend.config;

import com.proyecto.backend.entities.Paciente;
import com.proyecto.backend.repositories.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PacienteRepository repository) {
        return args -> {
            // Solo inserta si la tabla de pacientes tiene 5 o menos registros
            if (repository.count() <= 5) {
                String[] nombres = {"Carlos", "María", "José", "Ana", "Luis", "Sofia", "Juan", "Elena", "Pedro", "Laura", "Diego", "Lucía"};
                String[] apellidos = {"Gómez", "López", "Martínez", "Hernández", "García", "Pérez", "Rodríguez", "Sánchez", "Ramírez", "Flores"};
                Random random = new Random();

                for (int i = 1; i <= 100; i++) {
                    Paciente paciente = new Paciente();
                    paciente.setNombre(nombres[random.nextInt(nombres.length)]);
                    paciente.setApellidos(apellidos[random.nextInt(apellidos.length)]);
                    paciente.setEmail("paciente" + i + "@correo.com");
                    LocalDate fechaInicio = LocalDate.of(1970, 1, 1);
                    LocalDate fechaFin = LocalDate.of(2005, 12, 31);
                    long diasEntreFechas = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
                    paciente.setFechaNacimiento(fechaInicio.plusDays(random.nextLong(diasEntreFechas + 1)));

                    repository.save(paciente);
                }
                
                System.out.println("Se pobló la base de datos con 100 pacientes de prueba.");
            }
        };
    }
}
