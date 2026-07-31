import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProtectedComponent } from './pages/protected/protected.component';
import { authGuard } from './core/auth/auth.guard';
import { PatchNotesComponent } from './pages/patch-notes/patch-notes.component';
import { GameComponent } from './pages/game/game.component';
import { ShopComponent } from './pages/shop/shop.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Accueil'
  },
  {
    path: 'game',
    component: GameComponent,
    canActivate: [authGuard],
    title: 'Jeu'
  },
  {
    path: 'shop',
    component: ShopComponent,
    canActivate: [authGuard],
    title: 'Boutique'
  },
  {
    path: 'patch-notes',
    component: PatchNotesComponent,
    title: 'Patch Notes'
  },
  {
    path: '**',
    redirectTo: ''
  }
];
