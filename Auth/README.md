# Auth: servicio de autenticación

Servicio REST de autenticación desarrollado con Java 17 y Spring Boot 3.2.4. Gestiona usuarios y roles en PostgreSQL, valida credenciales y emite tokens JWT firmados con RSA para que los consuma el [Backend](../Backend/README.md).

## Tecnologías

- Spring Web, Spring Security y Spring Data JPA.
- PostgreSQL.
- JJWT y llaves RSA.
- Maven y Lombok.

## Endpoints

La ruta base es `/api/auth`. Por defecto, el servicio escucha en el puerto `8081`.

### Registrar usuario

`POST /api/auth/register`

```json
{
  "username": "usuario",
  "email": "usuario@example.com",
  "password": "cambia-esta-clave",
  "rol": "USER"
}
```

El campo `rol` es opcional; si se omite, se asigna `ROLE_USER`. La respuesta confirma el registro o informa si el nombre de usuario ya existe.

### Iniciar sesión

`POST /api/auth/login`

```json
{
  "username": "usuario",
  "password": "cambia-esta-clave"
}
```

Si las credenciales son válidas, la respuesta contiene el token:

```json
{
  "token": "<jwt>"
}
```

Las credenciales inválidas producen una respuesta `401`. El token tiene una duración predeterminada de 24 horas, configurable mediante `jwt.expiration-ms`.

## Requisitos y ejecución local

Se requiere Java 17, Maven y una base PostgreSQL con las tablas de usuarios y roles ya creadas. Hibernate valida el esquema (`ddl-auto=validate`) y no lo crea.

Desde esta carpeta:

```bash
mvn spring-boot:run
```

Para compilar o probar:

```bash
mvn test
mvn package
```

## Configuración

La configuración se encuentra en `src/main/resources/application.properties`:

| Variable o propiedad | Uso |
| --- | --- |
| `PORT` | Puerto HTTP; por defecto `8081` |
| `DB_HOST`, `DB_PORT`, `DB_NAME` | Ubicación de PostgreSQL |
| `DB_USER`, `DB_PASSWORD` | Credenciales de PostgreSQL |
| `jwt.expiration-ms` | Duración del JWT en milisegundos |

El código carga las llaves desde `src/main/resources/keys/private_key.pem` y `public_key.pem` en el classpath. Estas rutas están fijadas en los componentes que firman y validan los tokens. En despliegues, administra la llave privada como secreto y comparte con Backend la llave pública correspondiente. La configuración actual de CORS permite cualquier origen.

## Docker

La orquestación usa `Docker/auth.dockerfile`. Desde la raíz del repositorio:

```bash
docker compose -f Docker/docker-compose.yml build auth
docker compose -f Docker/docker-compose.yml up -d auth
docker compose -f Docker/docker-compose.yml logs -f auth
```

La base `auth_db` se declara en `Docker/init-dbs.sql`; ese script crea la base de datos, no las tablas de usuarios y roles. Asegúrate de que el esquema requerido exista antes de iniciar el servicio.
