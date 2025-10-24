import { Component } from '@angular/core';
import { NavBar } from '../../nav-bar/nav-bar';

@Component({
  selector: 'app-article',
  imports: [NavBar],
  templateUrl: './article.html',
  styleUrl: './article.scss',
})
export class Article {}
