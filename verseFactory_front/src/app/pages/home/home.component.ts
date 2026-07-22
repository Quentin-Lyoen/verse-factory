import { Component, inject, OnInit } from '@angular/core';
import { Title, Meta } from '@angular/platform-browser';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { Dialog } from '@angular/cdk/dialog';
import { CreateAccountComponent } from '../../shared/create-account/create-account.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  imports: [RouterLink],
})
export class HomeComponent implements OnInit {
  private titleService = inject(Title);
  private metaService = inject(Meta);
  private router = inject(Router);
  private dialog = inject(Dialog);
  public authService = inject(AuthService);

  ngOnInit() {
    this.titleService.setTitle('Accueil - VerseFactory');
    this.metaService.updateTag({ name: 'description', content: 'Découvrez Verse Factory, l\'ultime plateforme pour gérer votre propre usine multidimensionnel.' });
  }

  public playIfLoggedIn() {
    if (this.authService.isLoggedIn) {
      this.router.navigate(['/game'])
    } else {
      this.authService.login()
    }
  }

  public openCreateAccountDialog() {
    this.dialog.open(CreateAccountComponent);
  }
}
