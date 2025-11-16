import { Component, inject, OnInit } from '@angular/core';
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
  private session = inject(Session);
  private router = inject(Router);
  private authService = inject(AuthService);
  private localUser: User | undefined = undefined;
  ngOnInit(): void {
    this.authService
      .me()
      .pipe(take(1))
      .subscribe((user: User) => (this.localUser = user));
    if (this.localUser != undefined) {
      console.log('connecté');
      this.router.navigate(['feed']);
    } else {
      console.log('pas connecté');
    }
  }
}
