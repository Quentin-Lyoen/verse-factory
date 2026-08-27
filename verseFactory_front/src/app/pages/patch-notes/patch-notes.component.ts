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
        version: 'v0.2.0',
        date: '26 Août 2026',
        badge: 'Mise à jour',
        title: 'Mise à jour des améliorations',
        description: 'Les améliorations font leur arrivé dans votre factory ainsi que quelques ajustements de gameplay !',
        categories: [
          {
            type: 'feature',
            title: 'Nouveautés',
            items: [
              '- Ajout des améliorations dans la boutique.',
              '- Ajout de l\'amélioration de la capacité de stockage de la factory.',
              '- Ajout d\'un cooldown pour récupérer l\'argent de la factory.'
            ]
          },
          {
            type: 'fix',
            title: 'Corrections',
            items: [
              '- Ajustement des prix de la boutique.'
            ]
          }
        ]
      },
      {
        version: 'v0.1.2',
        date: '24 Août 2026',
        badge: 'Corrections',
        title: 'Corrections de bugs et ajustements',
        description: 'Plusieurs bugs ont été corrigés et plusieurs ajustements ont été apportés.',
        categories: [
          {
            type: 'feature',
            title: 'Nouveautés',
            items: [
              '- Affichage du message et du pet quand on ouvre une boîte.',
              '- Ajout d\'une limitation du nombre de pets à 6.'
            ]
          },
          {
            type: 'fix',
            title: 'Corrections',
            items: [
              '- Affichage des messages d\'erreur lors de l\'achat de boîtes.',
              '- Affichage des messages d\'erreur lors de la création de compte.',
              '- Correction de l\'affichage lors de la création de compte.'
            ]
          }
        ]
      },
      {
        version: 'v0.1.1',
        date: '07 Août 2026',
        badge: 'Corrections',
        title: 'Correction de bugs',
        description: 'Correction de bugs mineurs et d\'autres petits ajouts.',
        categories: [
          {
            type: 'feature',
            title: 'Nouveautés',
            items: [
              '- Ajout d\'images pour illustrer les boîtes de la boutique.',
              '- Ajout de la pop-up d\'informations sur les boîtes de la boutique.',
              '- Possibilité de retirer un pet de la factory.',
            ]
          },
          {
            type: 'fix',
            title: 'Corrections',
            items: [
              '- Changement de la page de connexion pour correspondre au reste du site.',
              '- La bar de navigation reste afficher à l\'écran même quand on défile vers le bas.'
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