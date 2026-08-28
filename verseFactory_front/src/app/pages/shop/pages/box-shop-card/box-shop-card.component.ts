import { Component, inject, input, output } from "@angular/core";
import { Boxe } from "../../../../model/shop.model";
import { ShopService } from "../../../../services/shop.service";
import { BoxInfoComponent } from "../../../../shared/box-info/box-info.component";
import { Dialog } from "@angular/cdk/dialog";
import { Pet } from "../../../../model/factory.model";

@Component({
    selector: "app-box-shop-card",
    templateUrl: "./box-shop-card.component.html",
})
export class BoxShopCardComponent {
    private shopService = inject(ShopService);
    private dialog = inject(Dialog);
    public petInfo = output<Pet>();
    public errorMessage = output<string>();

    public boxe = input<Boxe>();

    public buyBoxe(): void {
        this.shopService.buyBoxe(this.boxe()!.id).subscribe({
            next: (pet) => {
                this.petInfo.emit(pet);
            },
            error: (err) => {
                const message = err?.error?.message || "Erreur lors de l'achat de la boîte !";
                this.errorMessage.emit(message);
            }
        });
    }

    public openModal(): void {
        this.dialog.open(BoxInfoComponent, {
            data: this.boxe(),
        });
    }
}