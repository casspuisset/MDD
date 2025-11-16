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
  public isLoggedIn: boolean = false;
  public user = signal<User | undefined>(undefined);
  private authService = inject(AuthService);
  private router = inject(Router);

  /**
   * Init datas of the session when user register or logged in manually
   * @param user user's datas
   */
  public logIn(user: User): void {
    this.user.set(user);
    this.isLogged.update(() => true);
  }

  /**
   * Disconnect current user
   */
  public logOut(): void {
    this.authService
      .logout()
      .pipe(
        take(1),
        tap(() => {
          this.isLogged.update(() => false);
          this.user.set(undefined);
          this.isLoggedIn = false;
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

  /**
   * Verify if user is currently connected
   */
  public checkAuth(): void {
    this.authService
      .me()
      .pipe(take(1))
      .subscribe({
        next: (user: User) => {
          this.user.set(user);
          this.isLogged.set(true);
          this.isLoggedIn = true;
        },
        error: (error) => {
          this.isLogged.set(false);
          this.isLoggedIn = false;
          console.error(error);
        },
      });
  }
}
