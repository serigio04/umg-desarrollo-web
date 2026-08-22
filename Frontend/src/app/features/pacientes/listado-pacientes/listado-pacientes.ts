import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { PacientesService } from '../services/pacientes';

@Component({
  selector: 'app-listado-pacientes',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div style="padding: 20px; font-family: sans-serif;">
      <h2>Listado de Pacientes</h2>
      <ul style="list-style-type: none; padding: 0;">
        <li *ngFor="let p of pacientes" style="padding: 10px; border-bottom: 1px solid #ccc;">
          <strong>{{ p.nombre }} {{ p.apellidos }}</strong> - ID: {{ p.id }}
        </li>
      </ul>
    </div>
  `
})
export class ListadoPacientesComponent implements OnInit {
  private pacientesService = inject(PacientesService);
  private platformId = inject(PLATFORM_ID); 
  
  pacientes: any[] = [];

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.pacientesService.obtenerPacientes().subscribe({
        next: (data) => this.pacientes = data,
        error: (err) => console.error('Error cargando pacientes:', err)
      });
    }
  }
}