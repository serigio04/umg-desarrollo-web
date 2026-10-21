# Backend: API de gestión clínica

API REST desarrollada con Java 17 y Spring Boot 3.2.4. Gestiona pacientes, hospitales, doctores, citas y el menú disponible para los roles autenticados.

## Tecnologías

- Spring Web, Spring Security y Spring Data JPA.
- PostgreSQL para los datos relacionales y MongoDB para datos NoSQL.
- JWT para validar las peticiones autenticadas; el backend utiliza la llave pública RSA compartida con [Auth](../Auth/README.md).
- Maven y Lombok.

## Endpoints

La URL base local es `http://localhost:8080`. Los endpoints protegidos requieren un JWT en la cabecera `Authorization: Bearer <token>`.

| Método | Ruta | Acceso |
| --- | --- | --- |
| `GET` | `/api/pacientes` | `ROLE_ADMIN` o `ROLE_OPERADOR` |
| `POST` | `/api/pacientes` | `ROLE_ADMIN` |
| `GET` | `/api/hospitales` | `ROLE_ADMIN` o `ROLE_OPERADOR` |
| `POST` | `/api/hospitales` | `ROLE_ADMIN` |
| `GET` | `/api/doctores` | `ROLE_ADMIN` o `ROLE_OPERADOR` |
| `POST` | `/api/doctores` | `ROLE_ADMIN` |
| `GET` | `/api/citas` | `ROLE_ADMIN` o `ROLE_OPERADOR` |
| `POST` | `/api/citas` | `ROLE_ADMIN` |
| `GET` | `/api/accesos/menu` | Usuario autenticado |

El listado de pacientes acepta filtros y paginación mediante parámetros de consulta definidos por `PacienteFilter`. Devuelve un objeto con los datos y metadatos de paginación.

## Requisitos y ejecución local

Se requiere Java 17 y Maven. Configura primero una base PostgreSQL con el esquema de la aplicación. Para iniciar el servicio desde esta carpeta:

```bash
mvn spring-boot:run
```

Para compilar y ejecutar las pruebas:

```bash
mvn test
mvn package
```

## Configuración

`src/main/resources/application.properties` define los valores predeterminados y admite configuración por variables de entorno:

| Variable | Uso |
| --- | --- |
| `PORT` | Puerto HTTP; por defecto `8080` |
| `DB_HOST`, `DB_PORT`, `DB_NAME` | Ubicación de PostgreSQL |
| `DB_USER`, `DB_PASSWORD` | Credenciales de PostgreSQL |
| `SPRING_DATA_MONGODB_URI` | URI de conexión a MongoDB |

Hibernate está configurado con `ddl-auto=none`: el servicio no crea ni actualiza automáticamente el esquema relacional. La base de datos y sus tablas deben prepararse antes de iniciar la aplicación. En Docker Compose, la conexión relacional se configura desde `Docker/docker-compose.yml`. La configuración actual de CORS permite cualquier origen.

## Docker

La imagen multietapa usada por Compose se define en `Docker/backend.dockerfile`. Desde la raíz del repositorio:

```bash
docker compose -f Docker/docker-compose.yml build backend
docker compose -f Docker/docker-compose.yml up -d backend
docker compose -f Docker/docker-compose.yml logs -f backend
```

También existe `Backend/Dockerfile`; la orquestación del proyecto usa el Dockerfile ubicado en `Docker/`.