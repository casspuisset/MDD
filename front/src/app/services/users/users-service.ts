import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserEditRequest } from '../../interfaces/user/user.edit.interface';
import { User } from '../../interfaces/user/user.interface';
import { UserEditResponseInterface } from '../../interfaces/user/userEditResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private pathService = 'api/user';
  private httpClient = inject(HttpClient);

  public getById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  public edit(editRequest: UserEditRequest): Observable<UserEditResponseInterface> {
    return this.httpClient.put<UserEditResponseInterface>(`${this.pathService}/edit`, editRequest);
  }
}
