import { AfterContentChecked, AfterContentInit, Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterLink } from '@angular/router';
import { Session } from '../../services/session/session';
import { AuthService } from '../../services/auth/auth-service';
import { take } from 'rxjs';
import { User } from '../../interfaces/user/user.interface';

@Component({
  selector: 'app-home',
  imports: [RouterLink, MatButtonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private localUser: User | undefined;

  /**
   * verify on init if user is already connected
   */
  ngOnInit(): void {
    this.authService
      .me()
      .pipe(take(1))
      .subscribe({
        next: (user: User) => {
          this.localUser = user;
          if (this.localUser !== undefined) {
            this.router.navigate(['feed']);
          }
        },
      });
  }
}
