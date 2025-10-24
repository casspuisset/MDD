import { Component, inject, OnInit } from '@angular/core';
import { Session } from '../../services/session/session';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  imports: [],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.scss',
})
export class NavBar implements OnInit {
  public session = inject(Session);
  private router = inject(Router);
  private isLogged: boolean = false;

  ngOnInit(): void {
    if (this.session.isLogged) {
      this.isLogged = true;
    }
  }
  logOut() {
    this.session.logOut();
    this.router.navigate(['/home']);
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
