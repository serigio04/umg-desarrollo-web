import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DoctorService, DoctorResponse } from '../services/doctor';

@Component({
  selector: 'app-listado-doctores',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './listado-doctores.html',
  styleUrl: './listado-doctores.css'
})
export class ListadoDoctoresComponent implements OnInit {
  private doctorService = inject(DoctorService);
  private cdr = inject(ChangeDetectorRef);

  doctores: DoctorResponse[] = [];
  cargando = true;

  ngOnInit(): void {
    this.obtenerDoctores();
  }

  obtenerDoctores(): void {
    this.cargando = true;
    this.doctorService.obtenerActivos().subscribe({
      next: (data) => {
        this.doctores = data;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error al cargar la lista de doctores', err);
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });
  }
}