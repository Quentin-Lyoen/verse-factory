import { Component, computed, inject, OnInit, signal } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";
import { FactoryService } from "../../services/factory.service";
import { PetCardComponent } from "./pages/pet-card/pet-card.component";
import { Meta, Title } from "@angular/platform-browser";
import { GameMenuComponent } from "../../shared/game-menu/game-menu.component";
import { HasRoleDirective } from "../../directives/has-role.directive";
import { Dialog } from "@angular/cdk/dialog";
import { AdminPanelComponent } from "./pages/admin-panel/admin-panel.component";

@Component({
    selector: "app-game",
    templateUrl: "./game.component.html",
    imports: [PetCardComponent, GameMenuComponent, HasRoleDirective],
})
export class GameComponent implements OnInit {
    private factoryService = inject(FactoryService);
    private titleService = inject(Title);
    private metaService = inject(Meta);
    private dialog = inject(Dialog);
    public factory = toSignal(this.factoryService.getCurrentFactory());
    public factoryPets = toSignal(this.factoryService.getCurrentFactoryPets());

    public isPetsExpanded = signal<boolean>(false);
    public readonly initialVisiblePetsCount = 4;

    public displayedPets = computed(() => {
        const pets = this.factoryPets();
        if (!pets) return [];
        if (this.isPetsExpanded() || pets.length <= this.initialVisiblePetsCount) {
            return pets;
        }
        return pets.slice(0, this.initialVisiblePetsCount);
    });

    public togglePetsExpanded(): void {
        this.isPetsExpanded.update(expanded => !expanded);
    }

    public cooldownSeconds = this.factoryService.cooldownSeconds;

    public addPet(){
        this.factoryService.addPetInFactory("54eebc99-9c0b-4ef8-bb6d-6bb9bd380a20");
    }

    public updateBalance(){
        this.factoryService.updateFactoryBalance();
    }

    public openAdminPanel() {
        this.dialog.open(AdminPanelComponent);
    }

    ngOnInit() {
        this.titleService.setTitle('Jeu - VerseFactory');
        this.metaService.updateTag({ name: 'description', content: 'Découvrez Verse Factory, l\'ultime plateforme pour gérer votre propre usine multidimensionnel.' });
    }
}