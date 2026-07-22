import { Component, inject, OnInit } from "@angular/core";
import { Title, Meta } from "@angular/platform-browser";
import { RouterLink } from "@angular/router";
import { CommonModule } from "@angular/common";
import { PatchNote } from "../../model/patch-note.model";
import { PatchNoteCardComponent } from "./patch-note-card/patch-note-card.component";

@Component({
    selector: 'app-patch-notes',
    templateUrl: './patch-notes.component.html',
    imports: [RouterLink, CommonModule, PatchNoteCardComponent],
})
export class PatchNotesComponent implements OnInit {
    private titleService = inject(Title);
    private metaService = inject(Meta);

    public patchNotes: PatchNote[] = [
      {
        version: 'v1.0.0',
        date: '1 Juillet 2026',
        badge: 'Lancement',
        title: 'Lancement Officiel de Verse Factory',
        description: 'Bienvenue dans la première version officielle de Verse Factory ! Créez, gérez et développez votre usine multidimensionnelle.',
        categories: [
          {
            type: 'feature',
            title: 'Nouveautés',
            items: [
              'Déploiement du système de gestion d\'usine et de collecte d\'éléments.',
              'Système d\'authentification sécurisé et création de compte.',
              'Interface utilisateur intuitive avec thème personnalisé.'
            ]
          }
        ]
      }
    ];

    ngOnInit() {
      this.titleService.setTitle('Patch Notes - VerseFactory');
      this.metaService.updateTag({ name: 'description', content: 'Consultez l\'historique des mises à jour et nouveautés de Verse Factory.' });
    }
}