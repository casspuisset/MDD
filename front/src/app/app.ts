import { Component, inject, OnInit, signal } from '@angular/core';
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
export class App implements OnInit {
  protected readonly title = signal('front');
  private authService = inject(AuthService);
  private router = inject(Router);
  private session = inject(Session);

  public ngOnInit(): void {
    this.autoLog();
  }

  public $isLogged(): Observable<boolean> {
    return this.session.$isLogged();
  }

  public logout(): void {
    this.session.logOut();
    this.router.navigate(['/home']);
  }

  public autoLog(): void {
    this.authService.me().subscribe({
      next: (user: User) => {
        this.session.logIn(user);
      },
      error: () => {
        this.session.logOut();
      },
    });
  }
}
