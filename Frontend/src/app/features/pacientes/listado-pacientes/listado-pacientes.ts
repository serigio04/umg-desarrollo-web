import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PacientesService, PaginacionMetadata } from '../../../features/pacientes/services/pacientes';

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

  pacientes: any[] = [];
  cargando: boolean = true;
  errorMsg: string = '';

  // Filtros
  filtros = {
    nombre: '',
    apellido: '',
    dpi: '',
    estado: null as boolean | null,
    page: 0,
    size: 50
  };

  metadata: PaginacionMetadata = {
    totalRecords: 0,
    page: 0,
    pageSize: 50,
    totalPages: 0,
    hasPreviousPage: false,
    hasNextPage: false
  };

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.cargarPacientes();
    }
  }

  cargarPacientes() {
    this.cargando = true;
    this.pacientesService.obtenerPacientesPaginados(this.filtros).subscribe({
      next: (res) => {
        this.pacientes = res.data;
        this.metadata = res.metadata;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar pacientes:', err);
        this.errorMsg = 'Error al cargar el listado de pacientes.';
        this.cargando = false;
      }
    });
  }

  aplicarFiltros() {
    this.filtros.page = 0; // Regresa a la primera página ante un cambio de filtro
    this.cargarPacientes();
  }

  limpiarFiltros() {
    this.filtros = {
      nombre: '',
      apellido: '',
      dpi: '',
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