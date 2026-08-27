import { Component, inject, input } from "@angular/core";
import { FactoryPet } from "../../../../model/factory.model";
import { FactoryService } from "../../../../services/factory.service";

@Component({
    selector: 'app-pet-card',
    templateUrl: './pet-card.component.html',
})
export class PetCardComponent {
    public pet = input.required<FactoryPet>();
    public disableButton = input<boolean>(false);

    private factoryService = inject(FactoryService);

    public deletePetFromFactory(): void {
        this.factoryService.deletePetFromFactory(this.pet().id);
    }
}