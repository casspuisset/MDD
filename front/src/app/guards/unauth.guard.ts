import { inject, Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Session } from '../services/session/session';

@Injectable({ providedIn: 'root' })
export class UnauthGuard implements CanActivate {
  private router = inject(Router);
  private session = inject(Session);

  public canActivate(): boolean {
    if (this.session.isLogged()) {
      console.log('guard triggered');

      this.router.navigate(['feed']);
      return false;
    }
    return true;
  }
}
