import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject } from "@angular/core";
import { PetService } from "../../../../services/pet.service";
import { toSignal } from "@angular/core/rxjs-interop";
import { FactoryService } from "../../../../services/factory.service";

@Component({
    selector: 'app-admin-panel',
    templateUrl: './admin-panel.component.html'
})
export class AdminPanelComponent {
    private dialogRef = inject(DialogRef);
    private petService = inject(PetService);
    private factoryService = inject(FactoryService);

    public allPets = toSignal(this.petService.getAllPets());

    public addPetToFactory(id: string): void {
        this.factoryService.addPetInFactory(id);
    }

    public closeModal() {
        this.dialogRef.close();
    }
}