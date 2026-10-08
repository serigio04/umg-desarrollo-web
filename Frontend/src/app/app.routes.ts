import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login';
import { ListadoPacientesComponent } from './features/pacientes/listado-pacientes/listado-pacientes';
import { AgregarPacienteComponent } from './features/pacientes/agregar-paciente/agregar-paciente';

import { authGuard } from './core/guards/auth-guard';
import { roleGuard } from './core/guards/role-guard';
import { AgregarDoctorComponent } from './features/doctores/agregar-doctor/agregar-doctor';
import { ListadoHospitalesComponent } from './features/hospitales/listado-hospitales/listado-hospitales';

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
  { 
    path: 'hospitales', 
    component: ListadoHospitalesComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_ADMIN', 'ROLE_OPERADOR'] }
  },
  { 
    path: 'doctores/agregar', 
    component:  AgregarDoctorComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_ADMIN'] }
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' } // Redirige por defecto al login
];