import { Component, inject, OnInit } from '@angular/core';
import { Session } from '../../services/session/session';
import { Router } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-nav-bar',
  imports: [MatIcon],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.scss',
})
export class NavBar {
  public session = inject(Session);
  private router = inject(Router);
  public isOpen: boolean = false;

  logOut() {
    this.session.logOut();
    this.router.navigate(['/home']);
  }

  toOpen() {
    this.isOpen = true;
  }
  toFeed() {
    this.router.navigate(['/feed']);
  }
  toTopics() {
    this.router.navigate(['/topics']);
  }
  toUser() {
    this.router.navigate(['/dashboard']);
  }
}
