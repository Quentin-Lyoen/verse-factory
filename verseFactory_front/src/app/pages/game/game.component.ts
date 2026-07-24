import { Component, inject } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";
import { FactoryService } from "../../services/factory.service";
import { PetCardComponent } from "./pages/pet-card/pet-card.component";

@Component({
    selector: "app-game",
    templateUrl: "./game.component.html",
    imports: [PetCardComponent,],
})
export class GameComponent {
    private factoryService = inject(FactoryService);
    public factory = toSignal(this.factoryService.getCurrentFactory());
    public factoryPets = toSignal(this.factoryService.getCurrentFactoryPets());

    public addPet(){
        this.factoryService.addPetInFactory("20eebc99-9c0b-4ef8-bb6d-6bb9bd380a19");
    }

    public updateBalance(){
        this.factoryService.updateFactoryBalance();
    }
}