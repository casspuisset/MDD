import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../../interfaces/topic/topic.interface';
import { TopicSubscribingResponse } from '../../interfaces/topic/topicSubscribingResponse.interface';

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

  public topicSubscribe(topicId: number): Observable<TopicSubscribingResponse> {
    return this.http.post<TopicSubscribingResponse>(`${this.pathApi}/subscribe/${topicId}`, null);
  }

  public topicUnsubscribe(topicId: number): Observable<TopicSubscribingResponse> {
    return this.http.delete<TopicSubscribingResponse>(`${this.pathApi}/unsubscribe/${topicId}`);
  }
}
