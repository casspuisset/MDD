import { Routes } from '@angular/router';
import { AllArticles } from './components/articles/all-articles/all-articles';
import { CreateArticles } from './components/articles/create-articles/create-articles';

export const routes: Routes = [
  { path: '', component: AllArticles },
  { path: 'create', component: CreateArticles },
];
