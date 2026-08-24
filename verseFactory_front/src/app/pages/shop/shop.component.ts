import { Component, inject, OnInit, signal } from "@angular/core";
import { Meta, Title } from "@angular/platform-browser";
import { RouterLink, RouterLinkActive } from "@angular/router";
import { GameMenuComponent } from "../../shared/game-menu/game-menu.component";
import { ShopCardComponent } from "./pages/shop-card/shop-card.component";
import { ShopService } from "../../services/shop.service";
import { toSignal } from "@angular/core/rxjs-interop";
import { FactoryService } from "../../services/factory.service";
import { Pet } from "../../model/factory.model";

@Component({
    selector: 'app-shop',
    templateUrl: './shop.component.html',
    imports: [GameMenuComponent, ShopCardComponent],
})
export class ShopComponent implements OnInit {
    private titleService = inject(Title);
    private metaService = inject(Meta);
    private shopService = inject(ShopService);
    private factoryService = inject(FactoryService);

    public boxes = toSignal(this.shopService.getBoxes());
    public factory = toSignal(this.factoryService.getCurrentFactory());
    public petName = signal('');
    public errorMessage = signal('');
    
    ngOnInit() {
        this.titleService.setTitle('Boutique - VerseFactory');
        this.metaService.updateTag({ name: 'description', content: 'Découvrez Verse Factory, l\'ultime plateforme pour gérer votre propre usine multidimensionnel.' });
    }

    public receivePet(pet: Pet): void {
        this.petName.set(pet.name);
    }

    public receiveErrorMessage(message: string): void {
        this.errorMessage.set(message);
    }
}