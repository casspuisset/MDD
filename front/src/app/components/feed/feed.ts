import { Component } from '@angular/core';
import { NavBar } from '../nav-bar/nav-bar';
import { MatButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-feed',
  imports: [NavBar, MatButton, RouterLink],
  templateUrl: './feed.html',
  styleUrl: './feed.scss',
})
export class Feed {}
