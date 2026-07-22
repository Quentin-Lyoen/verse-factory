import { Component, inject } from "@angular/core";
import { RouterLink } from "@angular/router";
import { AuthService } from "../../services/auth.service";
import { Dialog } from "@angular/cdk/dialog";
import { CreateAccountComponent } from "../create-account/create-account.component";

@Component({
    selector: "app-navigation-bar",
    templateUrl: "./navigation-bar.component.html",
    imports: [RouterLink]
})

export class NavigationBarComponent {
    public authService = inject(AuthService);
    private dialog = inject(Dialog);

    public openCreateAccount(): void {
        this.dialog.open(CreateAccountComponent);
    }
}