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
        version: 'v0.0.1',
        date: '28 Juillet 2026',
        badge: 'Version Preview',
        title: 'Lancement de la Preview de Verse Factory',
        description: 'Bienvenue dans la première version preview de Verse Factory ! Créez, gérez et développez votre usine multidimensionnelle.',
        categories: [
          {
            type: 'feature',
            title: 'Nouveautés',
            items: [
              'Première version du jeu disponible pour les testeurs.'
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