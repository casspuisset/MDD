import { AfterContentInit, Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../services/auth/auth-service';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Session } from '../../../services/session/session';
import { RegisterRequest } from '../../../interfaces/auth/registerRequest.interface';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { NavBar } from '../../nav-bar/nav-bar';
import { CustomValidator } from '../../../services/validators/custom-validator';
import { User } from '../../../interfaces/user/user.interface';
import { catchError, take, tap } from 'rxjs';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, MatIcon, RouterLink, MatIconButton, NavBar],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register implements OnInit {
  public onError = false;
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private session = inject(Session);
  private customValidator = inject(CustomValidator);
  public localUser: User | undefined;

  public form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    name: ['', [Validators.required, Validators.min(3)]],
    password: ['', [Validators.required, Validators.min(8), this.customValidator.passwordFormat]],
  });

  public registerErrors = signal<string>('');

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
   * create a new user account when form is valid, and update status in session's service
   */
  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService
      .register(registerRequest)
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
        },
        error: (error) => {
          console.log(error);
          this.registerErrors.set(error.error);
          throw error;
        },
      });
  }

  isError() {
    if (this.registerErrors().includes("l'utilisateur existe déjà")) {
      return true;
    }

    return false;
  }
}
