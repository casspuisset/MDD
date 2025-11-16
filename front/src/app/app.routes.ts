import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { Home } from './components/home/home';
import { Login } from './components/auth/login/login';
import { Register } from './components/auth/register/register';
import { Feed } from './components/feed/feed';
import { Dashboard } from './components/dashboard/dashboard';
import { Topics } from './components/topics/topics';
import { Create } from './components/articles/create/create';
import { Details } from './components/articles/article/details';
import { NotFound } from './components/not-found/not-found';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: Home },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'feed', canActivate: [AuthGuard], component: Feed },
  { path: 'article/new', canActivate: [AuthGuard], component: Create },
  { path: 'article/:id', canActivate: [AuthGuard], component: Details },
  { path: 'topics', canActivate: [AuthGuard], component: Topics },
  { path: 'dashboard', canActivate: [AuthGuard], component: Dashboard },
  { path: '**', component: NotFound },
];
