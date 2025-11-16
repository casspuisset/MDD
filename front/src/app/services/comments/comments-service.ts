import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentRequest } from '../../interfaces/comments/commentRequest.interface';
import { Comment } from '../../interfaces/comments/comment.interface';
import { CommentResponse } from '../../interfaces/comments/commentResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private pathApi = 'api/comments';
  private http = inject(HttpClient);

  public createComment(articleId: number, comment: CommentRequest): Observable<CommentResponse> {
    return this.http.post<CommentResponse>(`${this.pathApi}/${articleId}`, comment);
  }

  public getComments(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.pathApi}/${articleId}`);
  }
}
