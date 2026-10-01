import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
// import { PacienteService } from '../../../core/services/paciente.service'; // Descomenta esto cuando tengas tu servicio

@Component({
  selector: 'app-agregar-paciente',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './agregar-paciente.html'
})
export class AgregarPacienteComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  // private pacienteService = inject(PacienteService);

  pacienteForm: FormGroup;
  cargando = false;

  constructor() {
    // Definimos los campos y sus validaciones
    this.pacienteForm = this.fb.group({
      nombres: ['', [Validators.required, Validators.minLength(3)]],
      apellidos: ['', [Validators.required, Validators.minLength(3)]],
      //identificacion: ['', Validators.required], // DPI, Cédula, etc.
      fechaNacimiento: ['', Validators.required],
      //telefono: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      //direccion: [''] // Opcional
    });
  }

  guardarPaciente() {
    if (this.pacienteForm.invalid) {
      this.pacienteForm.markAllAsTouched(); // Muestra los errores si el usuario intentó guardar vacío
      return;
    }

    this.cargando = true;
    const datosPaciente = this.pacienteForm.value;
    
    console.log('Enviando al backend:', datosPaciente);

    // AQUÍ IRÁ TU LLAMADA AL BACKEND:
    /*
    this.pacienteService.crear(datosPaciente).subscribe({
      next: (res) => {
        alert('Paciente creado con éxito');
        this.router.navigate(['/pacientes']); // Redirige al listado
      },
      error: (err) => {
        console.error(err);
        this.cargando = false;
      }
    });
    */
    
    // Simulación temporal para probar el front:
    setTimeout(() => {
      alert('Simulación: Paciente guardado');
      this.cargando = false;
      this.router.navigate(['/pacientes']);
    }, 1000);
  }

  cancelar() {
    this.router.navigate(['/pacientes']);
  }
}