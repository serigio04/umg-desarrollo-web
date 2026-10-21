import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CitaService, CitaDto } from '../services/cita';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-agregar-cita',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './agregar-cita.html',
  styleUrl: './cita.css'
})
export class AgregarCitaComponent implements OnInit {
  private citaService = inject(CitaService);
  private http = inject(HttpClient);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  pacientes: any[] = [];
  doctores: any[] = [];
  hospitales: any[] = [];
  errorMensaje = '';

  citaForm: FormGroup = this.fb.group({
    pacienteId: ['', Validators.required],
    doctorId: ['', Validators.required],
    hospitalId: ['', Validators.required],
    fechaHora: ['', Validators.required],
    motivo: ['', [Validators.required, Validators.minLength(5)]],
    estado: ['PENDIENTE']
  });

  ngOnInit(): void {
    this.cargarListasCatalogos();
  }

  cargarListasCatalogos(): void {
    const url = environment.API_BACKEND_URL;
    this.http.get<any[]>(`${url}/api/pacientes`).subscribe(data => this.pacientes = data);
    this.http.get<any[]>(`${url}/api/doctores`).subscribe(data => this.doctores = data);
    this.http.get<any[]>(`${url}/api/hospitales`).subscribe(data => this.hospitales = data);
  }

  guardarCita(): void {
    if (this.citaForm.invalid) {
      this.citaForm.markAllAsTouched();
      return;
    }

    const nuevaCita: CitaDto = this.citaForm.value;

    this.citaService.crearCita(nuevaCita).subscribe({
      next: () => {
        // Si tiene éxito, regresamos a la lista de citas
        this.router.navigate(['/citas']);
      },
      error: (err) => {
        console.error('Error al agendar cita:', err);
        this.errorMensaje = 'No se pudo guardar la cita. Verifique los datos enviados.';
      }
    });
  }
}