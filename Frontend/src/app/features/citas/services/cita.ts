import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

export interface CitaDto {
  id?: number;
  pacienteId: number;
  nombrePaciente?: string;
  doctorId: number;
  nombreDoctor?: string;
  hospitalId: number;
  nombreHospital?: string;
  fechaHora: string;
  motivo: string;
  estado?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CitaService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.API_BACKEND_URL}/api/citas`;

  obtenerCitas(): Observable<CitaDto[]> {
    return this.http.get<CitaDto[]>(this.apiUrl);
  }

  crearCita(cita: CitaDto): Observable<CitaDto> {
    return this.http.post<CitaDto>(this.apiUrl, cita);
  }
}