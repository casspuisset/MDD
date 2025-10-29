import { Component, inject } from '@angular/core';
import { AuthService } from '../../../services/auth/auth-service';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Session } from '../../../services/session/session';
import { AuthSuccess } from '../../../interfaces/auth/authSuccess.interface';
import { RegisterRequest } from '../../../interfaces/auth/registerRequest.interface';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { NavBar } from '../../nav-bar/nav-bar';
import { CustomValidator } from '../../../services/validators/custom-validator';
import { User } from '../../../interfaces/user/user.interface';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, MatIcon, RouterLink, MatIconButton, NavBar],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  public onError = false;
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private session = inject(Session);
  private customValidator = inject(CustomValidator);

  public form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    name: ['', [Validators.required, Validators.min(3)]],
    password: ['', [Validators.required, Validators.min(8), this.customValidator.passwordFormat]],
  });

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.session.logIn(user);
          this.router.navigate(['/feed']);
        });
        this.router.navigate(['/feed']);
      },
      error: (error) => {
        this.onError = true;
        console.error(error);
      },
    });
  }
}
