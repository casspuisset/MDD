import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentRequest } from '../../interfaces/comments/commentRequest.interface';
import { Comment } from '../../interfaces/comments/comment.interface';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private pathApi = 'api/comments';
  private http = inject(HttpClient);

  public createComment(articleId: number, comment: CommentRequest): Observable<any> {
    return this.http.post(`${this.pathApi}/${articleId}`, comment);
  }

  public getComments(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.pathApi}/${articleId}`);
  }
}
