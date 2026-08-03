import { Component, inject, OnInit } from "@angular/core";
import { Meta, Title } from "@angular/platform-browser";
import { RouterLink, RouterLinkActive } from "@angular/router";
import { GameMenuComponent } from "../../shared/game-menu/game-menu.component";

@Component({
    selector: 'app-shop',
    templateUrl: './shop.component.html',
    imports: [GameMenuComponent],
})
export class ShopComponent implements OnInit {
    private titleService = inject(Title);
    private metaService = inject(Meta);
    
    ngOnInit() {
        this.titleService.setTitle('Boutique - VerseFactory');
        this.metaService.updateTag({ name: 'description', content: 'Découvrez Verse Factory, l\'ultime plateforme pour gérer votre propre usine multidimensionnel.' });
    }
}