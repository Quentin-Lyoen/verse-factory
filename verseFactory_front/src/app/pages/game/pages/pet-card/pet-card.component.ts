import { Component, input } from "@angular/core";
import { Pet } from "../../../../model/factory.model";

@Component({
    selector: 'app-pet-card',
    templateUrl: './pet-card.component.html',
})
export class PetCardComponent {
    public pet = input.required<Pet>();
}