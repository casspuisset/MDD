import { Component } from '@angular/core';
import { NavBar } from '../nav-bar/nav-bar';

@Component({
  selector: 'app-dashboard',
  imports: [NavBar],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard {}
