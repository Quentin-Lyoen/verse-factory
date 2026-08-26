import { Component, inject, OnInit } from "@angular/core";
import { Meta, Title } from "@angular/platform-browser";
import { GameMenuComponent } from "../../shared/game-menu/game-menu.component";
import { BoxShopComponent } from "./pages/box-shop/box-shop.component";
import { FactoryService } from "../../services/factory.service";
import { toSignal } from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-shop',
    templateUrl: './shop.component.html',
    imports: [GameMenuComponent, BoxShopComponent],
})
export class ShopComponent implements OnInit {
    private titleService = inject(Title);
    private metaService = inject(Meta);
    private factoryService = inject(FactoryService);

    
    public factory = toSignal(this.factoryService.getCurrentFactory());
    
    ngOnInit() {
        this.titleService.setTitle('Boutique - VerseFactory');
        this.metaService.updateTag({ name: 'description', content: 'Découvrez Verse Factory, l\'ultime plateforme pour gérer votre propre usine multidimensionnel.' });
    }
}