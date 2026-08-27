import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { jwtDecode } from 'jwt-decode';
import { tap } from 'rxjs';
// 1. Importa el archivo de entorno (Angular reemplazará este archivo al compilar para producción)
import { environment } from '../../../environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  
  // 2. Asigna la URL desde la variable de entorno
  private apiUrl = environment.API_AUTH_URL; 

  tieneRol(rolesPermitidos: string[]): boolean {
    const rolesUsuario = this.obtenerRoles();
    return rolesPermitidos.some(rol => rolesUsuario.includes(rol));
  }

  login(credentials: { username: string; password: string }) {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }

  obtenerRoles(): string[] {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          const decoded: any = jwtDecode(token);
          return decoded.roles || [];
        } catch (e) {
          return [];
        }
      }
    }
    return [];
  }
}