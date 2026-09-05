import { Component, inject, OnInit, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PacientesService, PaginacionMetadata } from '../services/pacientes';

@Component({
  selector: 'app-listado-pacientes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './listado-pacientes.html',
  styleUrl: './listado-pacientes.css'
})
export class ListadoPacientesComponent implements OnInit {
  private pacientesService = inject(PacientesService);
  private platformId = inject(PLATFORM_ID);
  private cdr = inject(ChangeDetectorRef);

  pacientes: any[] = [];
  cargando: boolean = true;
  errorMsg: string = '';

  filtros = {
    nombre: '',
    apellidos: '', 
    estado: null as boolean | null,
    page: 0,
    size: 50
  };

  metadata: PaginacionMetadata = {
    totalRecords: 0, page: 0, pageSize: 50, totalPages: 0, hasPreviousPage: false, hasNextPage: false
  };

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.cargarPacientes();
    } else {
      this.cargando = false;
    }
  }

  cargarPacientes() {
    this.cargando = true;
    this.errorMsg = '';
    
    this.pacientesService.obtenerPacientesPaginados(this.filtros).subscribe({
      next: (res) => {
        this.pacientes = res.data;
        this.metadata = res.metadata;
        this.cargando = false;
        this.cdr.detectChanges(); // Forzar actualización de vista
      },
      error: (err) => {
        console.error('Error HTTP al cargar pacientes:', err);
        if (err.status === 403) {
          this.errorMsg = 'Error 403: No tienes permisos o tu sesión ha expirado.';
        } else {
          this.errorMsg = 'Error al conectar con el servidor.';
        }
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  aplicarFiltros() {
    this.filtros.page = 0; 
    this.cargarPacientes();
  }

  limpiarFiltros() {
    this.filtros = {
      nombre: '',
      apellidos: '', 
      estado: null,
      page: 0,
      size: this.filtros.size
    };
    this.cargarPacientes();
  }

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 0 && nuevaPagina < this.metadata.totalPages) {
      this.filtros.page = nuevaPagina;
      this.cargarPacientes();
    }
  }

  cambiarTamanoPagina() {
    this.filtros.page = 0;
    this.cargarPacientes();
  }
}