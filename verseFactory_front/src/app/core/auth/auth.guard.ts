import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { KeycloakService } from './keycloak.service';

export const authGuard: CanActivateFn = (route, state) => {
  const keycloakService = inject(KeycloakService);

  if (keycloakService.isLoggedIn()) {
    return true;
  }

  keycloakService.login();
  return false;
};
