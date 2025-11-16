import { inject, Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Session } from '../services/session/session';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  private router = inject(Router);
  private session = inject(Session);
  public localUser = this.session.user;

  /**
   * Verify if user is connected
   * @returns true or false
   */
  public canActivate(): boolean {
    if (!this.session.isLogged()) {
      this.router.navigate(['home']);
      return false;
    }
    return true;
  }
}
