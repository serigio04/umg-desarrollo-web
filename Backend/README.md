# Backend API - Gestión de Pacientes

API RESTful desarrollada en Java y Spring Boot que provee los servicios necesarios para la gestión y persistencia de la información de los pacientes. 

## Stack Tecnológico

- Java 17+
- Spring Boot 3.x (Web, Data JPA, Security)
- Lombok (Reducción de código repetitivo)
- PostgreSQL / MySQL (Base de datos relacional)
- Maven (Gestor de dependencias)
- Docker (Contenerización)

## Diagrama de Componentes

Muestra cómo están estructurados los paquetes lógicos dentro del backend.

```mermaid
graph TD
    subgraph "Capa de Presentación"
        C[PacienteController]
    end
    subgraph "Capa de Seguridad"
        SF[JwtAuthenticationFilter]
    end
    subgraph "Capa de Negocio"
        SI[PacienteServiceImpl]
    end
    subgraph "Capa de Acceso a Datos"
        R[PacienteRepository]
        S[PacienteSpecification]
    end
    subgraph "Base de Datos"
        DB[(DB)]
    end

    SF -->|Filtra peticiones| C
    C -->|Mapea DTOs e invoca| SI
    SI -->|Combina Specification y Pageable| R
    R -->|Usa| S
    R -->|JPA/Hibernate| DB
```

## Diagrama de Clases (Dominio principal)

Estructura de las entidades, DTOs y Servicios principales relacionados con el Paciente.

```mermaid
classDiagram
    class Paciente {
        -Long id
        -String nombre
        -String apellidos
        -String email
        -LocalDate fechaNacimiento
        -Boolean estado
    }
    
    class PacienteDto {
        -Long id
        -String nombre
        -String apellidos
        -String email
        -LocalDate fechaNacimiento
    }

    class PacienteFilter {
        -String nombre
        -String apellidos
        -Boolean estado
        -Integer page
        -Integer size
    }

    class PacienteService {
        <<interface>>
        +obtenerPacientesPaginados(PacienteFilter filter) PageResponse
        +crearPaciente(PacienteDto dto) Paciente
    }

    class PacienteServiceImpl {
        -PacienteRepository repository
    }

    PacienteServiceImpl ..|> PacienteService
    PacienteServiceImpl --> Paciente
    PacienteServiceImpl --> PacienteDto
    PacienteServiceImpl --> PacienteFilter
```

## Diagrama de Secuencia: Listado con Filtros y Paginación

Muestra el flujo de ejecución cuando el cliente solicita la tabla de pacientes.

```mermaid
sequenceDiagram
    actor Cliente as Frontend (Angular)
    participant C as PacienteController
    participant S as PacienteServiceImpl
    participant Spec as PacienteSpecification
    participant R as PacienteRepository
    participant DB as Base de Datos

    Cliente->>C: GET /api/pacientes?nombre=Juan&page=0&size=50
    C->>S: obtenerPacientesPaginados(PacienteFilter)
    S->>S: Crear PageRequest (Pageable)
    S->>Spec: conFiltros(PacienteFilter)
    Spec-->>S: Retorna Specification<Paciente>
    S->>R: findAll(Specification, Pageable)
    R->>DB: Ejecuta SQL Dinámico (LIKE %Juan% LIMIT 50 OFFSET 0)
    DB-->>R: Retorna ResultSet
    R-->>S: Retorna Page<Paciente>
    S->>S: Construye PaginacionMetadata
    S-->>C: Retorna PageResponse<Paciente>
    C-->>Cliente: 200 OK (JSON)
```

## Cómo ejecutar el proyecto (Docker)

El proyecto incluye un Dockerfile multietapa y configuración para levantar todo el entorno mediante Maven y Docker.

1. Asegúrate de estar en el directorio /backend.
2. Compilar y empaquetar el proyecto (evitando tests para despliegue rápido):
   ```bash
   mvn clean package -DskipTests
   ```
3. Construir la imagen de Docker:
   ```bash
   docker build backend --no-cache.
   ```
4. Levantar el contenedor (o usar docker-compose si está disponible):
   ```bash
   docker up backend -d
   ```