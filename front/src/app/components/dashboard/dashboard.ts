import { AfterContentInit, Component, inject, OnInit, signal } from '@angular/core';
import { NavBar } from '../nav-bar/nav-bar';
import { UsersService } from '../../services/users/users-service';
import { Session } from '../../services/session/session';
import { TopicsService } from '../../services/topics/topics-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserEditRequest } from '../../interfaces/user/user.edit.interface';
import { Router } from '@angular/router';
import { take } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  imports: [NavBar, ReactiveFormsModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard {
  private userService = inject(UsersService);
  private sessionService = inject(Session);
  public topicsService = inject(TopicsService);
  public formBuilder = inject(FormBuilder);
  private router = inject(Router);

  public user = this.sessionService.user;

  public subcribedTopics = signal<string[]>([]);

  userProfile = this.formBuilder.group({
    username: [''],
    email: [''],
    password: [''],
  });

  /**
   * Modification of user's datas
   */
  public edit(): void {
    const editRequest = this.userProfile.value as UserEditRequest;
    this.userService
      .edit(editRequest)
      .pipe(take(1))
      .subscribe({
        next: () => {
          console.log('user updated');
          this.sessionService.checkAuth();
          this.router.navigate(['feed']);
        },
      });
  }

  /**
   * Function to unsubscribe to a topic
   * @param topicId id of the unsubscribed topic
   */
  public unsubscribe(topicId: number): void {
    this.topicsService
      .topicUnsubscribe(topicId)
      .pipe(take(1))
      .subscribe({
        next: () => {
          const user = this.user();
          if (user !== undefined && user.topics !== undefined) {
            user.topics = user.topics.filter((topic) => topic.id !== topicId);
          }
          this.router.navigate(['topics']);
        },
        error: () => {
          console.error('An error occured when unsubscribing');
        },
      });
  }

  /**
   * Verify for each topic if user is subscribed
   * @param topicId id of the topic
   * @returns true if user is subscribed and false if not
   */
  isSubscribed(topicId: number): boolean {
    return !!this.user()?.topics?.some((topic) => topic.id === topicId);
  }
}
