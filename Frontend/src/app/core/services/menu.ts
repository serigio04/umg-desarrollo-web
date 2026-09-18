import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface MenuOpcion {
  id: number;
  nombre: string;
  ruta: string;
  icono: string;
  subModulos: MenuOpcion[];
}

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.API_BACKEND_URL}/accesos/menu`;

  obtenerMenuDinamico(): Observable<MenuOpcion[]> {
    return this.http.get<MenuOpcion[]>(this.apiUrl);
  }
}