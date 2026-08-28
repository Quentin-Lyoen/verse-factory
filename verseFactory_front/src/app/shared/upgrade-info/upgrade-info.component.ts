import { DIALOG_DATA, DialogRef } from "@angular/cdk/dialog";
import { Component, inject } from "@angular/core";
import { FactoryUpgrade } from "../../model/factory.model";

@Component({
    selector: 'app-upgrade-info',
    templateUrl: './upgrade-info.component.html',
})
export class UpgradeInfoComponent {
    private dialogRef = inject(DialogRef);

    public upgrade: FactoryUpgrade = inject(DIALOG_DATA);

    public closeModal(): void {
        this.dialogRef.close();
    }
}