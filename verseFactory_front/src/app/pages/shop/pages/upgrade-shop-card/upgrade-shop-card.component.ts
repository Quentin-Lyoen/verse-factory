import { Component, inject, input } from "@angular/core";
import { BuyFactoryUpgrade, FactoryUpgrade } from "../../../../model/factory.model";
import { FactoryService } from "../../../../services/factory.service";

@Component({
    selector: 'app-upgrade-shop-card',
    templateUrl: 'upgrade-shop-card.component.html',
})

export class UpgradeShopCardComponent {
    private factoryService = inject(FactoryService);

    public upgrade = input<FactoryUpgrade>();

    public buyUpgrade(): void {
        const upgradeRequest: BuyFactoryUpgrade = {
            upgradeId: this.upgrade()!.upgradeId,
            price: this.upgrade()!.cost
        };
        this.factoryService.buyUpgrade(upgradeRequest);
    }
}