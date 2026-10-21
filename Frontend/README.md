# Frontend: aplicación web

Interfaz del sistema de gestión clínica, construida con Angular 22 y TypeScript. Permite iniciar sesión y administrar pacientes, hospitales, doctores y citas según las rutas y roles definidos en la aplicación.

## Requisitos

- Node.js compatible con Angular 22; la imagen Docker usa Node.js 24.
- npm (la versión declarada en `package.json` es npm 11).

No es necesario instalar Angular CLI globalmente: los comandos de npm utilizan la CLI local del proyecto.

## Desarrollo

Desde esta carpeta:

```bash
npm install
npm start
```

Abre `http://localhost:4200`. Para compilar o ejecutar las pruebas:

```bash
npm run build
npm test
```

## Funcionalidades y estructura

- `src/app/features/`: pantallas de inicio de sesión, pacientes, hospitales, doctores, citas y administración de usuarios.
- `src/app/core/services/`: comunicación con las API y manejo de autenticación.
- `src/app/core/guards/`: protección de rutas por autenticación y roles.
- `src/app/core/interceptors/`: inclusión del JWT en las peticiones HTTP y gestión de respuestas de autenticación.
- `src/app/layout/`: navegación y elementos compartidos de la interfaz.
- `src/environments/`: direcciones de las API para desarrollo y compilación de producción.

## Direcciones de las API

Las URL se configuran en `src/environments/environment.ts` y `environment.prod.ts` mediante `API_AUTH_URL` y `API_BACKEND_URL`. En este momento ambos archivos apuntan a los servicios desplegados en Render. Si ejecutas las API localmente o mediante Docker, actualiza estos valores y reinicia el servidor de desarrollo o vuelve a generar la compilación.

Las URL que consume el navegador deben ser accesibles desde el equipo cliente. Los nombres internos de servicios de Docker, como `backend` o `auth`, solo resuelven dentro de la red de contenedores.

## Producción y Docker

`npm run build` genera los archivos de producción en `dist/frontend-app/browser`. La imagen definida en `Docker/frontend.dockerfile` sirve esos archivos con Nginx y publica el puerto 80. Nginx redirige las rutas de la aplicación a `index.html`.

Aunque el repositorio contiene archivos de servidor Angular, la configuración actual de `angular.json` deshabilita SSR (`ssr: false`); la aplicación se compila y sirve como SPA estática.