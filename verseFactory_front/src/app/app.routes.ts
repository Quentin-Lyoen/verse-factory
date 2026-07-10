import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProtectedComponent } from './pages/protected/protected.component';
import { authGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Accueil'
  },
  {
    path: 'game',
    component: ProtectedComponent,
    canActivate: [authGuard],
    title: 'Jeu'
  },
  {
    path: '**',
    redirectTo: ''
  }
];
