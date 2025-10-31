import { AfterContentInit, Component, inject, OnInit, signal } from '@angular/core';
import { NavBar } from '../nav-bar/nav-bar';
import { UsersService } from '../../services/users/users-service';
import { Session } from '../../services/session/session';
import { TopicsService } from '../../services/topics/topics-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserEditRequest } from '../../interfaces/user/user.edit.interface';
import { CustomValidator } from '../../services/validators/custom-validator';
import { toSignal } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';

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
  // private customValidator = inject(CustomValidator);

  public user = toSignal(this.userService.getById(this.sessionService.user!.id.toString()), {
    initialValue: null,
  });

  public subcribedTopics = signal<string[]>([]);

  userProfile = this.formBuilder.group({
    username: [''],
    email: [''],
    password: [''],
  });

  public edit(): void {
    const editRequest = this.userProfile.value as UserEditRequest;
    this.userService.edit(editRequest).subscribe({
      next: () => {
        console.log('user updated');
        this.router.navigate(['/feed']);
      },
    });
  }

  public unsubscribe(topicId: number): void {
    this.topicsService.topicUnsubscribe(topicId).subscribe({
      next: () => {
        const user = this.user();
        if (user !== null && user.topics !== undefined) {
          user.topics = user.topics.filter((topic) => topic.id !== topicId);
        }
        this.router.navigate(['/topics']);
      },
      error: () => {
        console.error('An error occured when unsubscribing');
      },
    });
  }

  isSubscribed(topicId: number): boolean {
    return !!this.user()?.topics?.some((topic) => topic.id === topicId);
  }
}
