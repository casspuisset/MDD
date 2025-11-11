import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../../interfaces/articles/article.interface';
import { ArticlesResponse } from '../../interfaces/articles/articlesResponse.interface';
import { createArticle } from '../../interfaces/articles/createArticle.interface';

@Injectable({
  providedIn: 'root',
})
export class ArticlesService {
  private pathApi = 'api/articles';
  private http = inject(HttpClient);

  public getAllArticles(): Observable<ArticlesResponse> {
    return this.http.get<any>(this.pathApi);
  }

  public createArticle(createRequest: createArticle): Observable<any> {
    return this.http.post(`${this.pathApi}/new`, createRequest);
  }

  public getArticleById(articleId: number): Observable<Article> {
    return this.http.get<Article>(`${this.pathApi}/${articleId}`);
  }

  public getArticlesFromFeed(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.pathApi}/feed`);
  }
}
