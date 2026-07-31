import { Component, inject, OnInit } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";
import { FactoryService } from "../../services/factory.service";
import { PetCardComponent } from "./pages/pet-card/pet-card.component";
import { Meta, Title } from "@angular/platform-browser";
import { RouterLink, RouterLinkActive } from "@angular/router";

@Component({
    selector: "app-game",
    templateUrl: "./game.component.html",
    imports: [PetCardComponent, RouterLink, RouterLinkActive],
})
export class GameComponent implements OnInit {
    private factoryService = inject(FactoryService);
    private titleService = inject(Title);
    private metaService = inject(Meta);
    public factory = toSignal(this.factoryService.getCurrentFactory());
    public factoryPets = toSignal(this.factoryService.getCurrentFactoryPets());

    public addPet(){
        this.factoryService.addPetInFactory("20eebc99-9c0b-4ef8-bb6d-6bb9bd380a19");
    }

    public updateBalance(){
        this.factoryService.updateFactoryBalance();
    }

    ngOnInit() {
    this.titleService.setTitle('Jeu - VerseFactory');
    this.metaService.updateTag({ name: 'description', content: 'Découvrez Verse Factory, l\'ultime plateforme pour gérer votre propre usine multidimensionnel.' });
  }
}