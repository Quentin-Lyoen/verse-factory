import { Component, inject, linkedSignal, signal } from "@angular/core";
import { DialogRef } from '@angular/cdk/dialog';
import { AuthService } from "../../services/auth.service";
import { CreateAccountFormData, CreateAccountRequest } from "../../model/account.model";
import { form, FormField } from '@angular/forms/signals';
import { toSignal } from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-create-account',
    templateUrl: './create-account.component.html',
    imports: [
        FormField,
    ],
})
export class CreateAccountComponent {
    private dialogRef = inject(DialogRef);
    private authService = inject(AuthService);
    public errorMessage = signal('');

    private accountSignal = linkedSignal<CreateAccountFormData>(() => ({
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
    }));

    public accountForm = form(this.accountSignal);

    public handleSubmit(): void {
        const username = this.accountSignal().username;
        const email = this.accountSignal().email;
        const password = this.accountSignal().password;
        const confirmPassword = this.accountSignal().confirmPassword;
        if (password !== confirmPassword) {
            alert('Les mots de passe ne correspondent pas.');
            return;
        }

        const data: CreateAccountRequest = {
            name: username,
            email: email,
            password: password,
        }

        this.authService.createAccount(data).subscribe({
            next: () => {
                this.dialogRef.close();
            },
            error: (err) => {
                if (err.status === 409) {
                    this.errorMessage.set('Cette adresse e-mail est déjà utilisée.');
                } else {
                    this.errorMessage.set('Une erreur est survenue lors de la création du compte.');
                }
            }
        });
    }
}