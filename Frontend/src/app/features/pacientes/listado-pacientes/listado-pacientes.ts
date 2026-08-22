import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
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
          <strong>{{ p.nombre }} {{ p.apellido }}</strong> - ID: {{ p.id }}
        </li>
      </ul>
    </div>
  `
})
export class ListadoPacientesComponent implements OnInit {
  private pacientesService = inject(PacientesService);
  pacientes: any[] = [];

  ngOnInit() {
    this.pacientesService.obtenerPacientes().subscribe({
      next: (data) => this.pacientes = data,
      error: (err) => console.error('Error cargando pacientes:', err)
    });
  }
}