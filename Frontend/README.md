# Frontend - Gestión de Pacientes

Esta es la aplicación cliente del Sistema de Gestión de Pacientes. Está desarrollada utilizando Angular, aprovechando funcionalidades modernas como Standalone Components, Server-Side Rendering (SSR) y reactividad.

## Tecnologías Principales

- Angular (Versión 17+)
- RxJS (Manejo de flujos asíncronos)
- Tailwind CSS / Bootstrap (Estilos y responsividad)
- TypeScript

## Requisitos Previos

- Node.js (v18 o superior)
- Angular CLI instalado globalmente (npm install -g @angular/cli)

## Configuración y Ejecución local

1. Instalar las dependencias del proyecto:
   ```bash
   npm install
   ```
2. Iniciar el servidor de desarrollo:
   ```bash
   npm start
   ```
   (o alternativamente: ng serve)
3. Navegar a http://localhost:4200/. La aplicación se recargará automáticamente si cambias cualquiera de los archivos fuente.

## Estructura Principal

- src/app/core/services: Servicios HTTP para comunicarse con la API (ej. pacientes.service.ts).
- src/app/core/interceptors: Interceptores HTTP (como auth.interceptor.ts que inyecta el token JWT de forma segura evaluando el entorno del navegador).
- src/app/components: Componentes de interfaz (ej. listado-pacientes).

## Notas sobre SSR (Server-Side Rendering)

Este proyecto utiliza SSR. Para evitar errores al interactuar con APIs del navegador (como localStorage o window en los interceptores), asegúrate de condicionar la ejecución del código del lado del cliente utilizando isPlatformBrowser(this.platformId) o evaluando typeof window !== 'undefined'.