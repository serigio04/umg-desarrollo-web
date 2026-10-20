import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

export interface DoctorRequest {
  nombres: string;
  apellidos: string;
  especialidad: string;
  colegiado: string;
  telefono: string;
  hospitalId: number;
}

export interface DoctorResponse extends Omit<DoctorRequest, 'hospitalId'> {
  id: number;
  hospital: { id: number; nombre: string }; 
}

@Injectable({ providedIn: 'root' })
export class DoctorService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.API_BACKEND_URL}/api/doctores`;

  obtenerActivos(): Observable<DoctorResponse[]> {
    return this.http.get<DoctorResponse[]>(this.apiUrl);
  }

  crear(doctor: DoctorRequest): Observable<DoctorResponse> {
    return this.http.post<DoctorResponse>(this.apiUrl, doctor);
  }
}