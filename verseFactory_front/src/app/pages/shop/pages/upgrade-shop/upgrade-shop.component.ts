import { Component, inject, signal } from "@angular/core";
import { FactoryService } from "../../../../services/factory.service";
import { toSignal } from "@angular/core/rxjs-interop";
import { UpgradeShopCardComponent } from "../upgrade-shop-card/upgrade-shop-card.component";

@Component({
    selector: 'app-upgrade-shop',
    templateUrl: './upgrade-shop.component.html',
    imports: [UpgradeShopCardComponent],
})
export class UpgradeShopComponent {
    private factoryService = inject(FactoryService);

    public errorMessage = signal<string>('');

    public upgrades = toSignal(this.factoryService.getCurrentUpgrades());

    public updateErrorMessage(errorMessage: string): void {
        this.errorMessage.set(errorMessage);
    }
}