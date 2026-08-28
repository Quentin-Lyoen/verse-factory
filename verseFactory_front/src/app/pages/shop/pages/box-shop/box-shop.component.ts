import { Component, inject, signal } from "@angular/core";
import { Pet } from "../../../../model/factory.model";
import { toSignal } from "@angular/core/rxjs-interop";
import { ShopService } from "../../../../services/shop.service";
import { FactoryService } from "../../../../services/factory.service";
import { BoxShopCardComponent } from "../box-shop-card/box-shop-card.component";

@Component({
    selector: 'app-box-shop',
    templateUrl: './box-shop.component.html',
    imports: [BoxShopCardComponent],
})
export class BoxShopComponent {
    private shopService = inject(ShopService);

    public boxes = toSignal(this.shopService.getBoxes());
    public petName = signal('');
    public errorMessage = signal('');

    public receivePet(pet: Pet): void {
        this.petName.set(pet.name);
    }

    public receiveErrorMessage(message: string): void {
        this.errorMessage.set(message);
    }
}