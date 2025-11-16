import { inject, Injectable, signal } from '@angular/core';
import { catchError, take, tap } from 'rxjs';
import { User } from '../../interfaces/user/user.interface';
import { AuthService } from '../auth/auth-service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class Session {
  public isLogged = signal<boolean>(false);
  public user = signal<User | undefined>(undefined);
  private authService = inject(AuthService);
  private router = inject(Router);

  public logIn(user: User): void {
    this.user.set(user);
    this.isLogged.update(() => true);
  }

  public logOut(): void {
    this.authService
      .logout()
      .pipe(
        take(1),
        tap(() => {
          this.isLogged.update(() => false);
          this.user.set(undefined);
        }),
        catchError((error) => {
          console.error('Error on logout : ' + error);
          throw error;
        })
      )
      .subscribe(() => {
        this.router.navigate(['home']);
      });
  }

  public checkAuth(): void {
    this.authService
      .me()
      .pipe(take(1))
      .subscribe({
        next: (user: User) => {
          this.logIn(user);
        },
        error: (error) => {
          this.isLogged.set(false);
          console.error(error);
        },
      });
  }
}
