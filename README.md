# 🚀 Proyecto Fullstack Dockerizado

Este repositorio contiene la arquitectura base para una aplicación **Fullstack** moderna. La infraestructura está completamente dockerizada para garantizar la separación de responsabilidades y la consistencia del entorno de desarrollo.

La solución está compuesta por las siguientes tecnologías principales:
* **Frontend:** Angular (servido mediante Nginx).
* **Backend:** Spring Boot (Java).
* **Bases de Datos:** PostgreSQL (Relacional) y MongoDB (NoSQL).
* **Orquestación:** Docker Compose.

---

## 📁 Estructura del Proyecto

El proyecto está organizado de forma modular. Hemos separado intencionalmente el código fuente de las aplicaciones de los archivos de configuración de infraestructura:

```text
mi-proyecto/
├── Backend/                 # Código fuente de Spring Boot (Java / Maven)
├── Frontend/                # Código fuente de Angular (Node / Nginx)
└── Docker/                  # Centralización de infraestructura y contenedores
    ├── backend.Dockerfile   # Instrucciones de construcción para el Backend
    ├── frontend.Dockerfile  # Instrucciones de construcción para el Frontend
    ├── nginx.conf           # Configuración de enrutamiento del servidor web
    └── docker-compose.yml   # Orquestador maestro de todos los servicios
```

---

## 🔌 Servicios y Puertos

Una vez que la arquitectura se encuentre en ejecución, los servicios estarán disponibles en los siguientes puertos de la máquina host para su uso y desarrollo:

| Servicio | Contenedor | Puerto Local | Tecnología |
| :--- | :--- | :--- | :--- |
| **Frontend** | `angular_frontend` | `80` | Angular + Nginx |
| **Backend** | `spring_backend` | `8080` | Spring Boot |
| **BD Relacional** | `postgres_db` | `5432` | PostgreSQL |
| **BD NoSQL** | `mongo_db` | `27017` | MongoDB |

---

## 🌐 Comunicación de Red Interna

Los contenedores están configurados para operar bajo una red virtual privada gestionada por Docker. Debido a esto, la comunicación interna **no se realiza a través de `localhost`**, sino utilizando los nombres DNS automáticos asignados a cada servicio:

* El **Backend** se conecta a la base de datos relacional apuntando al host interno `postgres-db:5432`.
* El **Backend** se conecta a la base de datos NoSQL apuntando al host interno `mongo-db:27017`.
* El **Frontend** (que se ejecuta en el navegador del usuario) interactúa con la API del Backend a través de la ruta expuesta `http://localhost:8080`.

## Comandos 
En la carpeta de **Docker** para compilar el proyecto

`docker compose up -d --build` 

Comando para levantar sin compilar 

`docker compose up -d`

Comando para ver estado

`docker compose ps`

Comando para detener / encender 

`docker compose stop / start`

Ver servicios en tiempo real 

`docker compose logs -f / <servicio>`

## Conexion a la base de datos desde el contenedor 
Entrar al contenedor de docker
`docker exec -it postgres_db psql -U admin -d clinica_db`
