import { DIALOG_DATA, DialogRef } from "@angular/cdk/dialog";
import { Component, inject } from "@angular/core";
import { Boxe } from "../../model/shop.model";
import { ShopService } from "../../services/shop.service";
import { toSignal } from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-box-info',
    templateUrl: './box-info.component.html',
})
export class BoxInfoComponent {
    private dialogRef = inject(DialogRef);
    private shopService = inject(ShopService);

    public boxe: Boxe = inject(DIALOG_DATA);
    public pets = toSignal(this.shopService.getPetsByBoxId(this.boxe.id));

    public closeModal(): void {
        this.dialogRef.close();
    }
}