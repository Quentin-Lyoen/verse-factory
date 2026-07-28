import { inject, Injectable } from "@angular/core";
import { KeycloakService } from "../core/auth/keycloak.service";
import { HttpClient } from "@angular/common/http";
import { CreateAccountRequest } from "../model/account.model";
import { environment } from "../../environments/environment";

@Injectable({
    providedIn: "root"
})
export class AuthService {
    private keycloakService = inject(KeycloakService);
    private http = inject(HttpClient);
    private url = `${environment.apiUrl}/v1/common`;

    public get isLoggedIn(): boolean {
        return this.keycloakService.isLoggedIn();
    }

    public login(): void {
        this.keycloakService.login();
    }

    public logout(): void {
        this.keycloakService.logout();
    }

    public createAccount(account: CreateAccountRequest): { success: boolean, error: string } {
        let success: boolean = false;
        let error: string = '';
        this.http.post(`${this.url}/accounts`, account).subscribe({
            next: (res) => {
                success = true;
            },
            error: (err) => {
                if (err.status === 409) {
                    error = 'Cette adresse e-mail est déjà utilisée.';
                } else {
                    error = 'Une erreur est survenue lors de la création du compte.';
                }
            }
        });
        return { success, error };
    }
}