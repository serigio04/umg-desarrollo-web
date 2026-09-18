import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './layout/navbar/navbar';
import { SidebarComponent } from './layout/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent, SidebarComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  private router = inject(Router);

  // Muestra el Navbar solo si NO estamos en la página de login
  mostrarNavbar(): boolean {
    return this.router.url !== '/login' && this.router.url !== '/';
  }

  // Muestra el Sidebar solo si NO estamos en la página de login
  mostrarSidebar(): boolean {
    return this.router.url !== '/login' && this.router.url !== '/';
  }
}