import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DoctorService } from '../services/doctor';
import { HospitalService, Hospital } from '../../hospitales/services/hospital';

@Component({
  selector: 'app-agregar-doctor',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './agregar-doctor.html',
  styleUrl: './agregar-doctor.css'
})
export class AgregarDoctorComponent implements OnInit {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private doctorService = inject(DoctorService);
  private hospitalService = inject(HospitalService);

  doctorForm: FormGroup;
  hospitales: Hospital[] = [];
  cargando = false;

  constructor() {
    this.doctorForm = this.fb.group({
      nombres: ['', Validators.required],
      apellidos: ['', Validators.required],
      especialidad: ['', Validators.required],
      colegiado: ['', Validators.required],
      telefono: ['', Validators.required],
      hospitalId: ['', Validators.required] // Guarda el ID del hospital seleccionado
    });
  }

  ngOnInit(): void {
    // Carga los hospitales para el menú desplegable
    this.hospitalService.obtenerActivos().subscribe({
      next: (data) => this.hospitales = data,
      error: (err) => console.error('Error cargando hospitales', err)
    });
  }

  guardarDoctor() {
    if (this.doctorForm.invalid) {
      this.doctorForm.markAllAsTouched();
      return;
    }

    this.cargando = true;
    this.doctorService.crear(this.doctorForm.value).subscribe({
      next: () => {
        this.router.navigate(['/doctores']);
      },
      error: (err) => {
        console.error('Error al crear doctor', err);
        this.cargando = false;
      }
    });
  }
}