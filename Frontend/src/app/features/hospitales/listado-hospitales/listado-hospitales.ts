import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HospitalService, Hospital } from '../services/hospital';

@Component({
  selector: 'app-listado-hospitales',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './listado-hospitales.html',
  styleUrl: './listado-hospitales.css'
})
export class ListadoHospitalesComponent implements OnInit {
  private hospitalService = inject(HospitalService);
  private cdr = inject(ChangeDetectorRef);

  hospitales: Hospital[] = [];
  cargando = true;

  ngOnInit(): void {
    this.hospitalService.obtenerActivos().subscribe({
      next: (data) => {
        this.hospitales = data;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error al cargar hospitales', err);
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });
  }
}