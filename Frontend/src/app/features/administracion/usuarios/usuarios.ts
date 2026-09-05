import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css'
})
//todo quitar data mock
export class UsuariosComponent {
  usuarios = [
    { id: 1, username: 'prueba_doctor', rol: 'ROLE_ADMIN' },
    { id: 2, username: 'operador_citas', rol: 'ROLE_OPERADOR' }
  ];
}