import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class PacientesService {
  private http = inject(HttpClient);
  private apiUrl = environment.API_BACKEND_URL; 

  obtenerPacientes() {
    return this.http.get<any[]>(`${this.apiUrl}/pacientes`);
  }
}