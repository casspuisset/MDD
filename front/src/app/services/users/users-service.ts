import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserEditRequest } from '../../interfaces/user/user.edit.interface';
import { User } from '../../interfaces/user/user.interface';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private pathService = 'api/user';
  private httpClient = inject(HttpClient);

  public getById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  public edit(editRequest: UserEditRequest): Observable<any> {
    return this.httpClient.put(`${this.pathService}/edit`, editRequest);
  }
}
