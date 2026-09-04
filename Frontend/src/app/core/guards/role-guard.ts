import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);

  const rolesPermitidos = route.data['roles'] as string[];

  // Si la ruta no requiere roles específicos, permite el acceso
  if (!rolesPermitidos || rolesPermitidos.length === 0) {
    return true;
  }

  // Si el usuario tiene permiso, permite el acceso
  if (authService.tieneRol(rolesPermitidos)) {
    return true;
  }
  // Si no tiene permiso y estamos en el navegador, mostramos el aviso
  if (isPlatformBrowser(platformId)) {
    console.warn('Acceso denegado: el usuario no posee los roles requeridos.');
  }

  // Redirigir a una ruta segura sin bloquear la ejecución del servidor
  router.navigate(['/login']);
  return false;
};