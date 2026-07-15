import { Component, inject, input } from "@angular/core";
import { KeycloakService } from "../../core/auth/keycloak.service";
import { RouterLink } from "@angular/router";

@Component({
    selector: "app-navigation-bar",
    templateUrl: "./navigation-bar.component.html",
    styleUrls: ['./navigation-bar.component.scss'],
    imports: [RouterLink]
})

export class NavigationBarComponent {
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