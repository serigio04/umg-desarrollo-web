import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';
import { ListadoPacientesComponent } from './features/pacientes/listado-pacientes/listado-pacientes';
import { authGuard } from './core/guards/auth-guard';
import { roleGuard } from './core/guards/role-guard';

export const routes: Routes = [
  { 
    path: 'login', 
    component: LoginComponent },
  { 
    path: 'pacientes', 
    component: ListadoPacientesComponent, 
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMIN', 'OPERADOR'] } 
  },
  {
    path: 'administracion/usuarios',
    component: ListadoPacientesComponent, 
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMIN'] }
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' } // Redirige por defecto al login
];