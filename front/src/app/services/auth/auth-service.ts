import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../../interfaces/auth/loginRequest.interfgace';
import { AuthSuccess } from '../../interfaces/auth/authSuccess.interface';
import { RegisterRequest } from '../../interfaces/auth/registerRequest.interface';
import { User } from '../../interfaces/user/user.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private path = '/api/auth';
  private httpClient = inject(HttpClient);

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.path}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.path}/login`, loginRequest);
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.path}/me`);
  }
}
