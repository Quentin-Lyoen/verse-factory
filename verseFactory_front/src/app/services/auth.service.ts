import { inject, Injectable } from "@angular/core";
import { KeycloakService } from "../core/auth/keycloak.service";

@Injectable({
    providedIn: "root"
})
export class AuthService {
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