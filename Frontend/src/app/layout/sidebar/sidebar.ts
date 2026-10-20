import { Component, OnInit, inject, ChangeDetectorRef, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuService, MenuOpcion } from '../../core/services/menu';

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
  private platformId = inject(PLATFORM_ID);

  menuOpciones: MenuOpcion[] = [];
  isSidebarOpen = true; 
  modulosExpandidos: { [key: number]: boolean } = {}; 

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.menuService.obtenerMenuDinamico().subscribe({
        next: (menu) => {
          console.log('Menú cargado:', menu);
          this.menuOpciones = menu;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error al cargar menú', err);
          this.cdr.detectChanges();
        }
      });
    }
  }

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  toggleSubmenu(id: number): void {
    this.modulosExpandidos[id] = !this.modulosExpandidos[id];
    this.cdr.detectChanges();
  }
}