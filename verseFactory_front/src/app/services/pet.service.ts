import { inject, Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Pet } from "../model/factory.model";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class PetService {
    private http = inject(HttpClient);
    private url = `${environment.apiUrl}/v1/admin/pets`;

    public getAllPets(): Observable<Pet[]> {
        return this.http.get<Pet[]>(this.url);
    }
}