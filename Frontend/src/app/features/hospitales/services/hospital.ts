import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

export interface Hospital {
  id?: number;
  nombre: string;
  direccion: string;
  telefono: string;
  activo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class HospitalService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.API_BACKEND_URL}/hospitales`;

  obtenerActivos(): Observable<Hospital[]> {
    return this.http.get<Hospital[]>(this.apiUrl);
  }

  crear(hospital: Hospital): Observable<Hospital> {
    return this.http.post<Hospital>(this.apiUrl, hospital);
  }
}