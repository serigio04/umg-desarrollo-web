import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

export interface PaginacionMetadata {
  totalRecords: number;
  page: number;
  pageSize: number;
  totalPages: number;
  hasPreviousPage: boolean;
  hasNextPage: boolean;
}

export interface PageResponse<T> {
  data: T[];
  metadata: PaginacionMetadata;
}

@Injectable({ providedIn: 'root' })
export class PacientesService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.API_BACKEND_URL}/api/pacientes`;

  obtenerPacientesPaginados(filtros: any): Observable<PageResponse<any>> {
    let params = new HttpParams();

    Object.keys(filtros).forEach(key => {
      if (filtros[key] !== null && filtros[key] !== undefined && filtros[key] !== '') {
        params = params.set(key, filtros[key]);
      }
    });

    return this.http.get<PageResponse<any>>(this.apiUrl, { params });
  }
}