import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { BehaviorSubject, Observable, switchMap } from "rxjs";
import { Boxe } from "../model/shop.model";

@Injectable({
    providedIn: "root",
})
export class ShopService {
    private http = inject(HttpClient);
    private url = `${environment.apiUrl}/v1/admin/boxes`;
    private boxesRefresh = new BehaviorSubject<void>(undefined);

    public getBoxes(): Observable<Boxe[]> {
        return this.boxesRefresh.pipe(
            switchMap(() => this.http.get<Boxe[]>(this.url))
        );
    }
}