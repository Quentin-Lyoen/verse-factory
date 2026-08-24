import { Component, inject, input, output } from "@angular/core";
import { Boxe } from "../../../../model/shop.model";
import { ShopService } from "../../../../services/shop.service";
import { BoxInfoComponent } from "../../../../shared/box-info/box-info.component";
import { Dialog } from "@angular/cdk/dialog";
import { Pet } from "../../../../model/factory.model";

@Component({
    selector: "app-shop-card",
    templateUrl: "./shop-card.component.html",
})
export class ShopCardComponent {
    private shopService = inject(ShopService);
    private dialog = inject(Dialog);
    public petInfo = output<Pet>();

    public boxe = input<Boxe>();

    public buyBoxe(): void {
        this.shopService.buyBoxe(this.boxe()!.id).subscribe({
            next: (pet) => {
                this.petInfo.emit(pet);
            },
            error: (err) => {
                console.error(err);
                alert("Solde insuffisant !");
            }
        });
    }

    public openModal(): void {
        this.dialog.open(BoxInfoComponent, {
            data: this.boxe(),
        });
    }
}