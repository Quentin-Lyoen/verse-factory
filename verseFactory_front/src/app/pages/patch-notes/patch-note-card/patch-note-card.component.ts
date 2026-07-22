import { Component, input } from "@angular/core";
import { PatchNote } from "../../../model/patch-note.model";

@Component({
    selector: 'app-patch-note-card',
    templateUrl: './patch-note-card.component.html',
})
export class PatchNoteCardComponent {
    public note = input.required<PatchNote>();
    public first = input<boolean>(false);
}