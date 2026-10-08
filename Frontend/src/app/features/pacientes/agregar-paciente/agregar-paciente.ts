import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PacientesService } from '../services/pacientes';

@Component({
  selector: 'app-agregar-paciente',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './agregar-paciente.html',
  styleUrl: './agregar-paciente.css'
})
export class AgregarPacienteComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private pacienteService = inject(PacientesService);

  pacienteForm: FormGroup;
  cargando = false;

  constructor() {
    this.pacienteForm = this.fb.group({
      nombres: ['', [Validators.required, Validators.minLength(3)]],
      apellidos: ['', [Validators.required, Validators.minLength(3)]],
      identificacion: ['', Validators.required], 
      fechaNacimiento: ['', Validators.required],
      telefono: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      direccion: [''] 
    });
  }

  guardarPaciente() {
    if (this.pacienteForm.invalid) {
      this.pacienteForm.markAllAsTouched(); 
      return;
    }

    this.cargando = true;
    const datosPaciente = this.pacienteForm.value;

    this.pacienteService.agregarPaciente(datosPaciente).subscribe({
      next: (res) => {
        this.cargando = false;
        this.router.navigate(['/pacientes']); 
      },
      error: (err) => {
        console.error('Error al guardar el paciente', err);
        this.cargando = false;
      }
    });
  }

  cancelar() {
    this.router.navigate(['/pacientes']);
  }
}