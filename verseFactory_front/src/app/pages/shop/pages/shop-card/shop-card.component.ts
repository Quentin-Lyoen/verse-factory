import { Component, input } from "@angular/core";
import { Boxe } from "../../../../model/shop.model";

@Component({
    selector: "app-shop-card",
    templateUrl: "./shop-card.component.html",
})
export class ShopCardComponent {
    public boxe = input<Boxe>();
}