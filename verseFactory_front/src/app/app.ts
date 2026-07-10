import { Component, inject, signal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { KeycloakService } from './core/auth/keycloak.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('verseFactory_front');
  
  private keycloakService = inject(KeycloakService);

  get isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn();
  }

  login(): void {
    this.keycloakService.login();
  }

  logout(): void {
    this.keycloakService.logout();
  }
}
