import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { Observable, tap } from "rxjs";
import { Boxe } from "../model/shop.model";
import { Pet } from "../model/factory.model";
import { FactoryService } from "./factory.service";

@Injectable({
    providedIn: "root",
})
export class ShopService {
    private http = inject(HttpClient);
    private factoryService = inject(FactoryService);
    private url = `${environment.apiUrl}/v1/admin/boxes`;

    public getBoxes(): Observable<Boxe[]> {
        return this.http.get<Boxe[]>(this.url);
    }

    public buyBoxe(id: string): Observable<Pet> {
        return this.http.post<Pet>(`${this.url}/${id}/open`, {}).pipe(
            tap(() => {
                this.factoryService.refreshFactory();
                this.factoryService.refreshPets();
            })
        );
    }
}