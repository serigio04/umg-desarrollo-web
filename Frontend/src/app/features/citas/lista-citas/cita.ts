import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CitaService, CitaDto } from '../services/cita';

@Component({
  selector: 'app-citas',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cita.html',
  styleUrls: ['./cita.css']
})
export class CitasComponent implements OnInit {
  private citaService = inject(CitaService);

  citas: CitaDto[] = [];
  cargando = false;

  ngOnInit(): void {
    this.cargarCitas();
  }

  cargarCitas(): void {
    this.cargando = true;
    this.citaService.obtenerCitas().subscribe({
      next: (data) => {
        this.citas = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener citas:', err);
        this.cargando = false;
      }
    });
  }
}