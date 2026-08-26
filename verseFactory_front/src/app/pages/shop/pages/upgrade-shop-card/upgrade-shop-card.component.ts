import { Component, input } from "@angular/core";
import { FactoryUpgrade } from "../../../../model/factory.model";

@Component({
    selector: 'app-upgrade-shop-card',
    templateUrl: 'upgrade-shop-card.component.html',
})

export class UpgradeShopCardComponent {
    public upgrade = input<FactoryUpgrade>()
}