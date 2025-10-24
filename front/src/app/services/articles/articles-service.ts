import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ArticlesResponse } from '../../interfaces/articlesResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class ArticlesService {
  private pathApi = 'api/articles';
  private http = inject(HttpClient);

  public getAllArticles(): Observable<ArticlesResponse> {
    return this.http.get<any>(this.pathApi);
  }

  public createArticle(form: FormData): Observable<any> {
    return this.http.post(this.pathApi, form);
  }

  public getArticleById(articleId: number): Observable<any> {
    return this.http.get<any>(`${this.pathApi}/${articleId}`);
  }

  public getArticlesFromFeed(userId: number): Observable<any> {
    return this.http.get<any>(`${this.pathApi}/user/${userId}`);
  }
}
