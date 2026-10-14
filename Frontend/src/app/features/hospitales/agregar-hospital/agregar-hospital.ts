import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HospitalService } from '../services/hospital';

@Component({
  selector: 'app-agregar-hospital',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './agregar-hospital.html',
  styleUrl: './agregar-hospital.css'
})
export class AgregarHospitalComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private hospitalService = inject(HospitalService);

  hospitalForm: FormGroup = this.fb.group({
    nombre: ['', [Validators.required, Validators.minLength(3)]],
    direccion: ['', Validators.required],
    telefono: ['', [Validators.required, Validators.pattern('^[0-9+\\s-]{8,}$')]]
  });

  cargando = false;

  guardarHospital(): void {
    if (this.hospitalForm.invalid) {
      this.hospitalForm.markAllAsTouched();
      return;
    }

    this.cargando = true;
    this.hospitalService.crear(this.hospitalForm.value).subscribe({
      next: () => {
        this.cargando = false;
        this.router.navigate(['/hospitales']);
      },
      error: (err) => {
        console.error('Error al registrar hospital:', err);
        this.cargando = false;
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/hospitales']);
  }
}