import { inject, Injectable } from "@angular/core";
import { KeycloakService } from "../core/auth/keycloak.service";
import { HttpClient } from "@angular/common/http";
import { CreateAccountRequest } from "../model/account.model";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";

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

    public createAccount(account: CreateAccountRequest): Observable<void> {
        return this.http.post<void>(`${this.url}/accounts`, account);
    }
}