import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { BehaviorSubject, Observable, switchMap } from "rxjs";
import { Factory, FactoryPet, Pet } from "../model/factory.model";
import { environment } from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class FactoryService {
    private http = inject(HttpClient);
    private url = `${environment.apiUrl}/v1/admin/factory`;
    private petRefresh = new BehaviorSubject<void>(undefined);
    private factoryRefresh = new BehaviorSubject<void>(undefined);

    public getCurrentFactory(): Observable<Factory> {
        return this.factoryRefresh.pipe(
            switchMap(() => this.http.get<Factory>(this.url))
        );
    }

    public getCurrentFactoryPets(): Observable<FactoryPet[]> {
        return this.petRefresh.pipe(
            switchMap(() => this.http.get<FactoryPet[]>(`${this.url}/pets`))
        );
    }

    public refreshFactory(): void {
        this.factoryRefresh.next();
    }

    public refreshPets(): void {
        this.petRefresh.next();
    }

    public addPetInFactory(petId: string): void{
        this.http.post<Pet>(`${this.url}/pets`, { petId }).subscribe({
            next: () => {
                this.petRefresh.next();
            }
        });
    }

    public updateFactoryBalance(): void{
        this.http.post<Factory>(`${this.url}/update-balance`, {}).subscribe({
            next: () => {
                this.factoryRefresh.next();
            }
        });
    }

    public deletePetFromFactory(factoryPetId: string): void {
        this.http.delete<void>(`${this.url}/pets/${factoryPetId}`).subscribe({
            next: () => {
                this.petRefresh.next();
            }
        });
    }
}