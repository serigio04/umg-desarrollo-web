import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { MenuService, MenuOpcion } from '../../core/services/menu';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.css']
})
export class SidebarComponent implements OnInit {
  menuService = inject(MenuService);
  private cdr = inject(ChangeDetectorRef);

  menuOpciones: MenuOpcion[] = [];
  isSidebarOpen = true; 
  modulosExpandidos: { [key: number]: boolean } = {}; 

  ngOnInit(): void {
    this.menuService.obtenerMenuDinamico().subscribe({
      next: (menu) => {
        console.log('Menú cargado:', menu);
        this.menuOpciones = menu;
        this.cdr.markForCheck(); // Notificacion a angular al detectar el cambio
      },
      error: (err) => console.error('Error al cargar menú', err)
    });
  }

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  toggleSubmenu(id: number): void {
    this.modulosExpandidos[id] = !this.modulosExpandidos[id];
    this.cdr.markForCheck();
  }
}