import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
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
  styleUrls: ['./agregar-cita.css']
})
export class AgregarCitaComponent implements OnInit {
  private citaService = inject(CitaService);
  private http = inject(HttpClient);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

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
    
    this.http.get<any>(`${url}/api/pacientes`).subscribe(res => {
      // Si tu backend lo envía paginado usa res.data, sino usa res directo
      this.pacientes = res.data || res; 
      this.cdr.detectChanges();
    });
    
    this.http.get<any>(`${url}/api/doctores`).subscribe(res => {
      this.doctores = res.data || res;
      this.cdr.detectChanges();
    });
    
    this.http.get<any>(`${url}/api/hospitales`).subscribe(res => {
      this.hospitales = res.data || res;
      this.cdr.detectChanges();
    });
  }

  guardarCita(): void {
    if (this.citaForm.invalid) {
      this.citaForm.markAllAsTouched();
      return;
    }

    const nuevaCita: CitaDto = this.citaForm.value;

    this.citaService.crearCita(nuevaCita).subscribe({
      next: () => {
        this.router.navigate(['/citas']);
      },
      error: (err) => {
        console.error('Error al agendar cita:', err);
        this.errorMensaje = 'No se pudo guardar la cita. Verifique los datos enviados.';
        this.cdr.detectChanges();
      }
    });
  }
}