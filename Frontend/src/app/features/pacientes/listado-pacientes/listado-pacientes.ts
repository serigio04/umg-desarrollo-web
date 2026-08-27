import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { PacientesService } from '../services/pacientes';

@Component({
  selector: 'app-listado-pacientes',
  standalone: true,
  imports: [CommonModule],
  template: './listado-pacientes.html',
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