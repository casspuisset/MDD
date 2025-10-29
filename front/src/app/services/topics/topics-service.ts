import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../../interfaces/topic/topic.interface';

@Injectable({
  providedIn: 'root',
})
export class TopicsService {
  private pathApi = 'api/topics';
  private http = inject(HttpClient);

  public getAllTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.pathApi}/all`);
  }

  public getFeedTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.pathApi}/feed`);
  }

  public topicSubscribe(topicId: number): Observable<any> {
    return this.http.post<any>(`${this.pathApi}/subscribe/${topicId}`, null);
  }

  public topicUnsubscribe(topicId: number): Observable<any> {
    return this.http.delete<any>(`${this.pathApi}/unsubscribe/${topicId}`);
  }
}
