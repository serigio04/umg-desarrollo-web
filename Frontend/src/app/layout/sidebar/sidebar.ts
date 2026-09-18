import { Component, OnInit, inject } from '@angular/core';
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
  menuOpciones: MenuOpcion[] = [];
  
  isSidebarOpen = true;
  modulosExpandidos: { [key: number]: boolean } = {}; 
  
  ngOnInit(): void {
    this.menuService.obtenerMenuDinamico().subscribe({
      next: (menu) => {
        console.log('Menú cargado:', menu);
        this.menuOpciones = menu;
      },
      error: (err) => console.error('Error al cargar menú', err)
    });
  }

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  toggleSubmenu(id: number): void {
    this.modulosExpandidos[id] = !this.modulosExpandidos[id];
  }
}