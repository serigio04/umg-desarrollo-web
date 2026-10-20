import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CitaService, CitaDto } from './services/cita';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-citas',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './citas.html',
  styleUrls: ['./citas.css']
})
export class CitasComponent implements OnInit {
  private citaService = inject(CitaService);
  private http = inject(HttpClient);
  private fb = inject(FormBuilder);

  citas: CitaDto[] = [];
  pacientes: any[] = [];
  doctores: any[] = [];
  hospitales: any[] = [];

  mostrarModal = false;
  cargando = false;
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
    this.cargarCitas();
    this.cargarListasCatalogos();
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

  cargarListasCatalogos(): void {
    const url = environment.API_BACKEND_URL;
    this.http.get<any[]>(`${url}/api/pacientes`).subscribe(data => this.pacientes = data);
    this.http.get<any[]>(`${url}/api/doctores`).subscribe(data => this.doctores = data);
    this.http.get<any[]>(`${url}/api/hospitales`).subscribe(data => this.hospitales = data);
  }

  abrirModal(): void {
    this.mostrarModal = true;
    this.errorMensaje = '';
    this.citaForm.reset({ estado: 'PENDIENTE' });
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }

  guardarCita(): void {
    if (this.citaForm.invalid) {
      this.citaForm.markAllAsTouched();
      return;
    }

    const nuevaCita: CitaDto = this.citaForm.value;

    this.citaService.crearCita(nuevaCita).subscribe({
      next: () => {
        this.cargarCitas();
        this.cerrarModal();
      },
      error: (err) => {
        console.error('Error al agendar cita:', err);
        this.errorMensaje = 'No se pudo guardar la cita. Verifique los datos enviados.';
      }
    });
  }
}