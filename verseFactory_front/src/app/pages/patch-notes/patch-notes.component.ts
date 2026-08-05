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
        version: 'v0.1.1',
        date: '05 Août 2026',
        badge: 'Corrections',
        title: 'Correction de bugs',
        description: 'Correction de bugs mineurs et d\'autres petits ajouts.',
        categories: [
          {
            type: 'fix',
            title: 'Corrections',
            items: [
              '- Changement de la page de connexion pour correspondre au reste du site.',
              '- La bar de navigation reste afficher à l\'écran même quand on défile vers le bas.'
            ]
          },
          {
            type: 'feature',
            title: 'Nouveautés',
            items: [
              '- Ajout d\'images pour illustrer les boîtes de la boutique.',
            ]
          }
        ]
      },
      {
        version: 'v0.1.0',
        date: '04 Août 2026',
        badge: 'Mise à jour',
        title: 'Mise à jour du shop',
        description: 'La boutique de boîte vient d\'être ajoutée, il est maintenant possible d\'acheter des boîtes pour obtenir des pets.',
        categories: [
          {
            type: 'feature',
            title: 'Nouveautés',
            items: [
              '- Ajout de la boutique.',
              '- Possibilité d\'acheter une boîte pour obtenir un pet.',
              '- Boîte débutant : Chien, Chat et Licorne',
              '- Boîte épique : Chat, Licorne et Dragon'
            ]
          }
        ]
      },
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
              '- Première version du jeu disponible pour les testeurs.'
            ]
          }
        ]
      },
    ];

    ngOnInit() {
      this.titleService.setTitle('Patch Notes - VerseFactory');
      this.metaService.updateTag({ name: 'description', content: 'Consultez l\'historique des mises à jour et nouveautés de Verse Factory.' });
    }
}