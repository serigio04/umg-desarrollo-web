import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Leemos los roles requeridos desde la configuración de la ruta
  const rolesPermitidos = route.data['roles'] as string[];

  // Si no se definieron roles para la ruta, permitimos el acceso
  if (!rolesPermitidos || rolesPermitidos.length === 0) {
    return true;
  }

  // Si tiene el rol, pasa. Si no, lo redirigimos al inicio o a una página de "Acceso Denegado"
  if (authService.tieneRol(rolesPermitidos)) {
    return true;
  } else {
    alert('No tienes permisos para acceder a esta página.');
    router.navigate(['/pacientes']); // Redirigir a una ruta segura
    return false;
  }
};