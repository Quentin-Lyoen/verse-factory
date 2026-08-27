import { HttpClient } from "@angular/common/http";
import { inject, Injectable, signal } from "@angular/core";
import { BehaviorSubject, Observable, switchMap, tap } from "rxjs";
import { BuyFactoryUpgrade, Factory, FactoryPet, FactoryUpgrade, Pet } from "../model/factory.model";
import { environment } from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class FactoryService {
    private http = inject(HttpClient);
    private url = `${environment.apiUrl}/v1/admin/factory`;
    private petRefresh = new BehaviorSubject<void>(undefined);
    private factoryRefresh = new BehaviorSubject<void>(undefined);
    private upgradeRefresh = new BehaviorSubject<void>(undefined);

    public cooldownSeconds = signal<number>(0);
    private cooldownInterval: ReturnType<typeof setInterval> | null = null;
    private readonly COOLDOWN_KEY = 'balance_update_cooldown_end';
    private readonly BASE_COOLDOWN_DURATION_SEC = 10;
    private currentCooldownDurationSec = 10;

    constructor() {
        this.initCooldown();
        this.loadUpgrades();
    }

    private isBrowser(): boolean {
        return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
    }

    private loadUpgrades(): void {
        this.getCurrentUpgrades().subscribe({
            next: (upgrades) => {
                this.updateCooldownFromUpgrades(upgrades);
            },
            error: () => {}
        });
    }

    private updateCooldownFromUpgrades(upgrades: FactoryUpgrade[]): void {
        const cooldownUpgrade = upgrades.find(u => u.upgradeId === 'BALANCE_COOLDOWN');
        const level = cooldownUpgrade ? cooldownUpgrade.level : 0;
        this.currentCooldownDurationSec = Math.max(1, this.BASE_COOLDOWN_DURATION_SEC - level);
    }

    private initCooldown(): void {
        if (!this.isBrowser()) return;

        const savedEndTime = localStorage.getItem(this.COOLDOWN_KEY);
        if (savedEndTime) {
            const endTime = parseInt(savedEndTime, 10);
            const remaining = Math.ceil((endTime - Date.now()) / 1000);
            if (remaining > 0) {
                this.startCooldownTimer(endTime);
            } else {
                localStorage.removeItem(this.COOLDOWN_KEY);
            }
        }
    }

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
        if (this.cooldownSeconds() > 0) return;

        const endTime = Date.now() + this.currentCooldownDurationSec * 1000;
        if (this.isBrowser()) {
            localStorage.setItem(this.COOLDOWN_KEY, endTime.toString());
        }
        this.startCooldownTimer(endTime);

        this.http.post<Factory>(`${this.url}/update-balance`, {}).subscribe({
            next: () => {
                this.factoryRefresh.next();
            }
        });
    }

    private startCooldownTimer(endTime: number): void {
        this.clearCooldownTimer();

        const updateRemaining = () => {
            const remaining = Math.ceil((endTime - Date.now()) / 1000);
            if (remaining <= 0) {
                this.cooldownSeconds.set(0);
                if (this.isBrowser()) {
                    localStorage.removeItem(this.COOLDOWN_KEY);
                }
                this.clearCooldownTimer();
            } else {
                this.cooldownSeconds.set(remaining);
            }
        };

        updateRemaining();
        this.cooldownInterval = setInterval(updateRemaining, 1000);
    }

    private clearCooldownTimer(): void {
        if (this.cooldownInterval) {
            clearInterval(this.cooldownInterval);
            this.cooldownInterval = null;
        }
    }

    public deletePetFromFactory(factoryPetId: string): void {
        this.http.delete<void>(`${this.url}/pets/${factoryPetId}`).subscribe({
            next: () => {
                this.petRefresh.next();
            }
        });
    }

    public getCurrentUpgrades(): Observable<FactoryUpgrade[]> {
        return this.upgradeRefresh.pipe(
            switchMap(() => this.http.get<FactoryUpgrade[]>(`${this.url}/upgrades`)),
            tap((upgrades) => this.updateCooldownFromUpgrades(upgrades))
        );
    }

    public buyUpgrade(upgradeRequest: BuyFactoryUpgrade): Observable<FactoryUpgrade>{
        return this.http.post<FactoryUpgrade>(`${this.url}/upgrades`, upgradeRequest).pipe(
            tap((purchasedUpgrade) => {
                if (purchasedUpgrade.upgradeId === 'BALANCE_COOLDOWN') {
                    this.currentCooldownDurationSec = Math.max(1, this.BASE_COOLDOWN_DURATION_SEC - purchasedUpgrade.level);
                }
                this.upgradeRefresh.next();
                this.factoryRefresh.next();
            })
        );
    }
}