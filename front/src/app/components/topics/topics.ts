import { Component, inject, OnInit, signal } from '@angular/core';
import { NavBar } from '../nav-bar/nav-bar';
import { toSignal } from '@angular/core/rxjs-interop';
import { TopicsService } from '../../services/topics/topics-service';
import { UsersService } from '../../services/users/users-service';
import { Session } from '../../services/session/session';
import { take } from 'rxjs';

@Component({
  selector: 'app-topics',
  imports: [NavBar],
  templateUrl: './topics.html',
  styleUrl: './topics.scss',
})
export class Topics {
  private topicService = inject(TopicsService);
  private userService = inject(UsersService);
  private sessionService = inject(Session);
  public user = this.sessionService.user;
  public topics = toSignal(this.topicService.getAllTopics(), {
    initialValue: [],
  });
  public subcribedTopics = signal<string[]>([]);

  //get all topics
  subscribe(topicId: number) {
    this.topicService
      .topicSubscribe(topicId)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.subcribedTopics.update((topics) => [...topics, topicId.toString()]);
        },
        error: () => console.error('An error occured during subscribing'),
      });
  }

  //verify for each topic if the user is subscribed to it
  isSubscribed(topicId: number): boolean {
    if (this.subcribedTopics().includes(topicId.toString())) {
      return true;
    }
    return !!this.user()?.topics?.some((topic) => topic.id === topicId);
  }
}
