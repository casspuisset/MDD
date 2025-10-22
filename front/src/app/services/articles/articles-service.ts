import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ArticlesResponse } from '../../interfaces/api/articlesResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class ArticlesService {
  public rentalForm: FormGroup | undefined;

  private pathApi = 'api/articles';
  constructor(private http: HttpClient) {}

  public all(): Observable<ArticlesResponse> {
    return this.http.get<any>(this.pathApi);
  }

  public createArticle(form: FormData): Observable<any> {
    return this.http.post(this.pathApi, form);
  }
}
