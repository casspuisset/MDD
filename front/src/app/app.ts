import { afterNextRender, Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Observable } from 'rxjs';
import { Session } from './services/session/session';
import { AuthService } from './services/auth/auth-service';
import { User } from './interfaces/user/user.interface';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatButtonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('front');
  private authService = inject(AuthService);
  private router = inject(Router);
  private session = inject(Session);

  constructor() {
    afterNextRender(() => {
      this.session.checkAuth();
    });
  }
}
