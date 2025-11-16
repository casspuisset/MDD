import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../services/auth/auth-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Session } from '../../../services/session/session';
import { LoginRequest } from '../../../interfaces/auth/loginRequest.interfgace';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { NavBar } from '../../nav-bar/nav-bar';
import { User } from '../../../interfaces/user/user.interface';
import { take } from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [MatIcon, ReactiveFormsModule, RouterLink, MatIconButton, RouterLink, NavBar],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login implements OnInit {
  public hide = true;
  public onError = false;
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  public session = inject(Session);
  public form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.min(3)]],
    password: ['', [Validators.required, Validators.min(3)]],
  });
  public loginErrors = signal<string>('');

  public localUser: User | undefined;

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

  /**
   * Submit the login request for authentication
   */
  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService
      .login(loginRequest)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.authService
            .me()
            .pipe(take(1))
            .subscribe((user: User) => {
              this.session.logIn(user);
              this.router.navigate(['feed']);
            });
          this.router.navigate(['feed']);
        },
        error: (error) => {
          this.loginErrors.update(() => 'error');
          console.error(error);
        },
      });
  }

  isError() {
    if (this.loginErrors().includes('error')) {
      return true;
    }
    return false;
  }
}
