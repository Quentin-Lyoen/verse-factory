import { Component, inject } from '@angular/core';
import { KeycloakService } from '../../core/auth/keycloak.service';

@Component({
  selector: 'app-protected',
  template: `
    <div style="padding: 2rem;">
      <h1>Zone Protégée</h1>
      <p>Félicitations, vous êtes connecté !</p>
      
      @if (profile) {
        <div style="margin-top: 1rem; padding: 1rem; background-color: #f0f0f0; border-radius: 8px;">
          <h3>Votre profil :</h3>
          <p><strong>Username:</strong> {{ profile.username }}</p>
          <p><strong>Email:</strong> {{ profile.email }}</p>
          <p><strong>Nom:</strong> {{ profile.firstName }} {{ profile.lastName }}</p>
        </div>
      }
    </div>
  `
})
export class ProtectedComponent {
  private keycloakService = inject(KeycloakService);
  profile = this.keycloakService.profile;
}
