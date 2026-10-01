import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';
import { ListadoPacientesComponent } from './features/pacientes/listado-pacientes/listado-pacientes';
import { AgregarPacienteComponent } from './features/pacientes/agregar-paciente/agregar-paciente';
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
    data: { roles: ['ROLE_ADMIN', 'ROLE_OPERADOR'] } 
  },
  { 
    path: 'pacientes/agregar', 
    component: AgregarPacienteComponent, 
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_OPERADOR'] } 
  },
  {
    path: 'administracion/usuarios',
    component: ListadoPacientesComponent, 
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_ADMIN'] }
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' } // Redirige por defecto al login
];