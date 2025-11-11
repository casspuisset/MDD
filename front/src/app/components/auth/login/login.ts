import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../../services/auth/auth-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Session } from '../../../services/session/session';
import { AuthSuccess } from '../../../interfaces/auth/authSuccess.interface';
import { LoginRequest } from '../../../interfaces/auth/loginRequest.interfgace';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { NavBar } from '../../nav-bar/nav-bar';
import { User } from '../../../interfaces/user/user.interface';

@Component({
  selector: 'app-login',
  imports: [MatIcon, ReactiveFormsModule, RouterLink, MatIconButton, RouterLink, NavBar],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  public hide = true;
  public onError = false;
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private session = inject(Session);
  public form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.min(3)]],
    password: ['', [Validators.required, Validators.min(3)]],
  });
  public loginErrors = signal<string>('');

  //on login, create a token and set it on localStorage
  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.session.logIn(user);
          this.router.navigate(['/feed']);
        });
        this.router.navigate(['/feed']);
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
